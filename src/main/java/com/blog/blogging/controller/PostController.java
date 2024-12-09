package com.blog.blogging.controller;

import com.blog.blogging.dto.PostDTO;
import com.blog.blogging.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO.Response> createPost(
        @Valid @RequestBody PostDTO.Create postDTO
    ) {
        PostDTO.Response createdPost = postService.createPost(postDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // Get all posts
    @GetMapping
    public ResponseEntity<List<PostDTO.Response>> getAllPosts() {
        List<PostDTO.Response> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // Get a single post by ID
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO.Response> getPostById(@PathVariable Long id) {
        PostDTO.Response post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    // Update an existing post
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO.Response> updatePost(
        @PathVariable Long id, 
        @Valid @RequestBody PostDTO.Create postDTO
    ) {
        PostDTO.Response updatedPost = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(updatedPost);
    }

    // Delete a post
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
