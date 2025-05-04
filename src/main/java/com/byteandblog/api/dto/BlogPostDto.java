package com.byteandblog.api.dto;

import com.byteandblog.api.model.BlogPost;

import java.time.LocalDateTime;
import java.util.List;

public class BlogPostDto {
    private Long id;
    private String title;
    private String excerpt;
    private String content;
    private String coverImage;
    private String coverImageCaption;
    private List<String> tags;
    private UserDto author;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public BlogPostDto() {
    }

    // Constructor with all fields
    public BlogPostDto(Long id, String title, String excerpt, String content, String coverImage,
                      String coverImageCaption, List<String> tags, UserDto author,
                      LocalDateTime publishedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.coverImage = coverImage;
        this.coverImageCaption = coverImageCaption;
        this.tags = tags;
        this.author = author;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Static factory method to create from entity
    public static BlogPostDto fromEntity(BlogPost blogPost) {
        return new BlogPostDtoBuilder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .excerpt(blogPost.getExcerpt())
                .content(blogPost.getContent())
                .coverImage(blogPost.getCoverImage())
                .coverImageCaption(blogPost.getCoverImageCaption())
                .tags(blogPost.getTags())
                .author(UserDto.fromEntity(blogPost.getAuthor()))
                .publishedAt(blogPost.getPublishedAt())
                .createdAt(blogPost.getCreatedAt())
                .updatedAt(blogPost.getUpdatedAt())
                .build();
    }

    // Builder pattern method
    public static BlogPostDtoBuilder builder() {
        return new BlogPostDtoBuilder();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImageCaption() {
        return coverImageCaption;
    }

    public void setCoverImageCaption(String coverImageCaption) {
        this.coverImageCaption = coverImageCaption;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Builder class
    public static class BlogPostDtoBuilder {
        private Long id;
        private String title;
        private String excerpt;
        private String content;
        private String coverImage;
        private String coverImageCaption;
        private List<String> tags;
        private UserDto author;
        private LocalDateTime publishedAt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        BlogPostDtoBuilder() {
        }

        public BlogPostDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BlogPostDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BlogPostDtoBuilder excerpt(String excerpt) {
            this.excerpt = excerpt;
            return this;
        }

        public BlogPostDtoBuilder content(String content) {
            this.content = content;
            return this;
        }

        public BlogPostDtoBuilder coverImage(String coverImage) {
            this.coverImage = coverImage;
            return this;
        }

        public BlogPostDtoBuilder coverImageCaption(String coverImageCaption) {
            this.coverImageCaption = coverImageCaption;
            return this;
        }

        public BlogPostDtoBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public BlogPostDtoBuilder author(UserDto author) {
            this.author = author;
            return this;
        }

        public BlogPostDtoBuilder publishedAt(LocalDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public BlogPostDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BlogPostDtoBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public BlogPostDto build() {
            return new BlogPostDto(id, title, excerpt, content, coverImage, coverImageCaption, 
                                  tags, author, publishedAt, createdAt, updatedAt);
        }
    }
}
