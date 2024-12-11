package com.blog.blogging.controller;

import com.blog.blogging.dto.CommentDTO;
import com.blog.blogging.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // Create a new comment
    @PostMapping("/post/{postId}")
    public ResponseEntity<String> createComment(
        @Valid @RequestBody CommentDTO.Create commentDTO,@PathVariable Long postId) {
        String createdComment = commentService.createComment(commentDTO,postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // Get comments for a specific post
    @GetMapping
    public ResponseEntity<List<CommentDTO.Response>> getCommentsByPostId(
        @RequestParam(name = "post_id") Long postId) {
        List<CommentDTO.Response> comments = commentService.getCommentsByPostId(postId);
        if (!comments.isEmpty())
        {
            return ResponseEntity.ok(comments);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    // Get a single comment
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO.Response> getCommentById(@PathVariable Long id) {
        try {
            CommentDTO.Response comment = commentService.getCommentById(id);
            return ResponseEntity.ok(comment);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a comment
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO.Response> updateComment(
        @PathVariable Long id, 
        @Valid @RequestBody CommentDTO.Create commentDTO) {
        CommentDTO.Response updatedComment = commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    // Delete a comment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}