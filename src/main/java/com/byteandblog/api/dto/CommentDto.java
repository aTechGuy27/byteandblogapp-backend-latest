package com.byteandblog.api.dto;

import com.byteandblog.api.model.Comment;

import java.time.LocalDateTime;

public class CommentDto {
    private Long id;
    private String content;
    private Long postId;
    private UserDto author;
    private LocalDateTime createdAt;

    // Default constructor
    public CommentDto() {
    }

    // Constructor with all fields
    public CommentDto(Long id, String content, Long postId, UserDto author, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.author = author;
        this.createdAt = createdAt;
    }

    // Static factory method to create from entity
    public static CommentDto fromEntity(Comment comment) {
        return new CommentDtoBuilder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getBlogPost().getId())
                .author(UserDto.fromEntity(comment.getAuthor()))
                .createdAt(comment.getCreatedAt())
                .build();
    }

    // Builder pattern method
    public static CommentDtoBuilder builder() {
        return new CommentDtoBuilder();
    }

    // Getters and Setters
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Builder class
    public static class CommentDtoBuilder {
        private Long id;
        private String content;
        private Long postId;
        private UserDto author;
        private LocalDateTime createdAt;

        CommentDtoBuilder() {
        }

        public CommentDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CommentDtoBuilder content(String content) {
            this.content = content;
            return this;
        }

        public CommentDtoBuilder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public CommentDtoBuilder author(UserDto author) {
            this.author = author;
            return this;
        }

        public CommentDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CommentDto build() {
            return new CommentDto(id, content, postId, author, createdAt);
        }
    }
}
