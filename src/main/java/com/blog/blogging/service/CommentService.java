package com.blog.blogging.service;

import com.blog.blogging.dto.CommentDTO;
import com.blog.blogging.exception.ResourceNotFoundException;
import com.blog.blogging.model.Comment;
import com.blog.blogging.model.Post;
import com.blog.blogging.repository.CommentRepository;
import com.blog.blogging.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PostRepository postRepository;
    // Create a new comment
    public String createComment(CommentDTO.Create commentDTO, Long postId) {
        objectMapper.registerModule(new JavaTimeModule());
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found "));
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPost(post);
        comment.setAuthorId(commentDTO.getAuthorId());
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        System.out.println(savedComment.getPost().getTitle());

        return "Comment Added ";
    }

    // Get comments for a specific post
    public List<CommentDTO.Response> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(comment -> {
                    CommentDTO.Response response = new CommentDTO.Response();
                    response.setId(comment.getId());
                    response.setContent(comment.getContent());
                    response.setPostId(comment.getPost());  // Set postId as Long
                    response.setAuthorId(comment.getAuthorId());
                    response.setCreatedAt(comment.getCreatedAt());
                    return response;
                })
                .collect(Collectors.toList());
    }

    // Get a single comment by ID
    public CommentDTO.Response getCommentById(Long id) {
        objectMapper.registerModule(new JavaTimeModule());

        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
         CommentDTO.Response response = new CommentDTO.Response();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setPostId(comment.getPost());  // Set postId as Long
        response.setAuthorId(comment.getAuthorId());
        response.setCreatedAt(comment.getCreatedAt());
        return response;
    }

    // Update an existing comment

    public CommentDTO.Response updateComment(Long id, CommentDTO.Create updatedCommentDTO) {
        objectMapper.registerModule(new JavaTimeModule());

        Comment existingComment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        System.out.println(updatedCommentDTO.getContent());
        existingComment.setContent(updatedCommentDTO.getContent());
        Comment comment = commentRepository.save(existingComment);
        CommentDTO.Response response = new CommentDTO.Response();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setPostId(comment.getPost());
        response.setAuthorId(comment.getAuthorId());
        response.setCreatedAt(comment.getCreatedAt());
        return response;
    }

    // Delete a comment
    public void deleteComment(Long id) {
        Comment existingComment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        
        commentRepository.delete(existingComment);
    }
}
