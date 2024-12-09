package com.blog.blogging.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long postId;
    private String content;
    private Long authorId;
    private LocalDateTime createdAt;
}