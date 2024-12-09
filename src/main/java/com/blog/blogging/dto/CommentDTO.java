package com.blog.blogging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CommentDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        @NotBlank(message = "Comment content cannot be blank")
        @Size(max = 1000, message = "Comment must be less than 1000 characters")
        private String content;

        private Long postId;
        private Long authorId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String content;
        private Long postId;
        private Long authorId;
        private LocalDateTime createdAt;
    }
}