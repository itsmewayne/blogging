package com.blog.blogging.dto;

import com.blog.blogging.model.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CommentDTO {

    public static class Create {
        @NotBlank(message = "Comment content cannot be blank")
        @Size(max = 1000, message = "Comment must be less than 1000 characters")
        private String content;

        private Long authorId;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }



        public Long getAuthorId() {
            return authorId;
        }

        public void setAuthorId(Long authorId) {
            this.authorId = authorId;
        }

        public Create(String content, Long authorId) {
            this.content = content;
            this.authorId = authorId;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String content;
        private Post post;
        private Long authorId;
        private LocalDateTime createdAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Post getPostId() {
            return post;
        }

        public void setPostId(Post postId) {
            this.post = postId;
        }

        public Long getAuthorId() {
            return authorId;
        }

        public void setAuthorId(Long authorId) {
            this.authorId = authorId;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }


    }
}