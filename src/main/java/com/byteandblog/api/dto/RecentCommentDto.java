package com.byteandblog.api.dto;

import com.byteandblog.api.model.Comment;

import java.time.LocalDateTime;

public class RecentCommentDto {
    private Long id;
    private String content;
    private Long postId;
    private String postTitle;
    private UserDto author;
    private LocalDateTime createdAt;

    // Default constructor
    public RecentCommentDto() {
    }

    // Constructor with all fields
    public RecentCommentDto(Long id, String content, Long postId, String postTitle, UserDto author, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.postTitle = postTitle;
        this.author = author;
        this.createdAt = createdAt;
    }

    // Static factory method to create from entity
    public static RecentCommentDto fromEntity(Comment comment) {
        return new RecentCommentDtoBuilder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getBlogPost().getId())
                .postTitle(comment.getBlogPost().getTitle())
                .author(UserDto.fromEntity(comment.getAuthor()))
                .createdAt(comment.getCreatedAt())
                .build();
    }

    // Builder pattern method
    public static RecentCommentDtoBuilder builder() {
        return new RecentCommentDtoBuilder();
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

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
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
    public static class RecentCommentDtoBuilder {
        private Long id;
        private String content;
        private Long postId;
        private String postTitle;
        private UserDto author;
        private LocalDateTime createdAt;

        RecentCommentDtoBuilder() {
        }

        public RecentCommentDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RecentCommentDtoBuilder content(String content) {
            this.content = content;
            return this;
        }

        public RecentCommentDtoBuilder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public RecentCommentDtoBuilder postTitle(String postTitle) {
            this.postTitle = postTitle;
            return this;
        }

        public RecentCommentDtoBuilder author(UserDto author) {
            this.author = author;
            return this;
        }

        public RecentCommentDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public RecentCommentDto build() {
            return new RecentCommentDto(id, content, postId, postTitle, author, createdAt);
        }
    }
}
