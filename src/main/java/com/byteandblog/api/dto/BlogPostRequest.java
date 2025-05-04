package com.byteandblog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class BlogPostRequest {
    
    @NotBlank(message = "Title is required")
    @Size(min = 5, message = "Title must be at least 5 characters")
    private String title;
    
    @NotBlank(message = "Excerpt is required")
    @Size(min = 10, message = "Excerpt must be at least 10 characters")
    private String excerpt;
    
    @NotBlank(message = "Content is required")
    @Size(min = 50, message = "Content must be at least 50 characters")
    private String content;
    
    private String coverImage;
    
    private String coverImageCaption;
    
    private List<String> tags;

    // Default constructor
    public BlogPostRequest() {
    }

    // Constructor with all fields
    public BlogPostRequest(String title, String excerpt, String content, 
                          String coverImage, String coverImageCaption, List<String> tags) {
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.coverImage = coverImage;
        this.coverImageCaption = coverImageCaption;
        this.tags = tags;
    }

    // Builder pattern method
    public static BlogPostRequestBuilder builder() {
        return new BlogPostRequestBuilder();
    }

    // Getters and Setters
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

    // Builder class
    public static class BlogPostRequestBuilder {
        private String title;
        private String excerpt;
        private String content;
        private String coverImage;
        private String coverImageCaption;
        private List<String> tags;

        BlogPostRequestBuilder() {
        }

        public BlogPostRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BlogPostRequestBuilder excerpt(String excerpt) {
            this.excerpt = excerpt;
            return this;
        }

        public BlogPostRequestBuilder content(String content) {
            this.content = content;
            return this;
        }

        public BlogPostRequestBuilder coverImage(String coverImage) {
            this.coverImage = coverImage;
            return this;
        }

        public BlogPostRequestBuilder coverImageCaption(String coverImageCaption) {
            this.coverImageCaption = coverImageCaption;
            return this;
        }

        public BlogPostRequestBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public BlogPostRequest build() {
            return new BlogPostRequest(title, excerpt, content, coverImage, coverImageCaption, tags);
        }
    }
}
