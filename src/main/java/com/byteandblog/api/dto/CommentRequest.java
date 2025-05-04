package com.byteandblog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentRequest {
    
    @NotBlank(message = "Content is required")
    @Size(min = 3, message = "Comment must be at least 3 characters")
    private String content;
    
    @NotNull(message = "Post ID is required")
    private Long postId;

    // Default constructor
    public CommentRequest() {
    }

    // Constructor with all fields
    public CommentRequest(String content, Long postId) {
        this.content = content;
        this.postId = postId;
    }

    // Builder pattern method
    public static CommentRequestBuilder builder() {
        return new CommentRequestBuilder();
    }

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    // Builder class
    public static class CommentRequestBuilder {
        private String content;
        private Long postId;

        CommentRequestBuilder() {
        }

        public CommentRequestBuilder content(String content) {
            this.content = content;
            return this;
        }

        public CommentRequestBuilder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public CommentRequest build() {
            return new CommentRequest(content, postId);
        }
    }
}
