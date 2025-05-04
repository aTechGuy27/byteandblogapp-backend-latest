package com.byteandblog.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blog_posts")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String excerpt;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "cover_image_caption")
    private String coverImageCaption;

    @ElementCollection
    @CollectionTable(name = "blog_post_tags", joinColumns = @JoinColumn(name = "blog_post_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor
    public BlogPost() {
    }

    // Constructor with all fields
    public BlogPost(Long id, String title, String excerpt, String content, String coverImage,
                   String coverImageCaption, List<String> tags, User author,
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

    // Builder pattern method
    public static BlogPostBuilder builder() {
        return new BlogPostBuilder();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (publishedAt == null) {
            publishedAt = createdAt;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
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
    public static class BlogPostBuilder {
        private Long id;
        private String title;
        private String excerpt;
        private String content;
        private String coverImage;
        private String coverImageCaption;
        private List<String> tags = new ArrayList<>();
        private User author;
        private LocalDateTime publishedAt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        BlogPostBuilder() {
        }

        public BlogPostBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BlogPostBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BlogPostBuilder excerpt(String excerpt) {
            this.excerpt = excerpt;
            return this;
        }

        public BlogPostBuilder content(String content) {
            this.content = content;
            return this;
        }

        public BlogPostBuilder coverImage(String coverImage) {
            this.coverImage = coverImage;
            return this;
        }

        public BlogPostBuilder coverImageCaption(String coverImageCaption) {
            this.coverImageCaption = coverImageCaption;
            return this;
        }

        public BlogPostBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public BlogPostBuilder author(User author) {
            this.author = author;
            return this;
        }

        public BlogPostBuilder publishedAt(LocalDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public BlogPostBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BlogPostBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public BlogPost build() {
            return new BlogPost(id, title, excerpt, content, coverImage, coverImageCaption, 
                               tags, author, publishedAt, createdAt, updatedAt);
        }
    }
}
