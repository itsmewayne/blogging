package com.blog.blogging.service;

import com.blog.blogging.dto.PostDTO;
import com.blog.blogging.exception.ResourceNotFoundException;
import com.blog.blogging.model.Post;
import com.blog.blogging.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private  PostRepository postRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    // Create a new post
    public PostDTO.Response createPost(PostDTO.Create postDTO) {
        objectMapper.registerModule(new JavaTimeModule());
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAuthorId(postDTO.getAuthorId());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);
        return objectMapper.convertValue(savedPost, PostDTO.Response.class);
    }

    // Get all posts
    public List<PostDTO.Response> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> objectMapper.convertValue(post, PostDTO.Response.class))
                .collect(Collectors.toList());
    }

    // Get post by ID
    public PostDTO.Response getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return objectMapper.convertValue(post, PostDTO.Response.class);
    }

    // Update a post
    public PostDTO.Response updatePost(Long id, PostDTO.Create postDTO) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        existingPost.setTitle(postDTO.getTitle());
        existingPost.setContent(postDTO.getContent());
        existingPost.setUpdatedAt(LocalDateTime.now());

        Post updatedPost = postRepository.save(existingPost);
        return objectMapper.convertValue(updatedPost, PostDTO.Response.class);
    }

    // Delete a post
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        postRepository.delete(post);
    }
}