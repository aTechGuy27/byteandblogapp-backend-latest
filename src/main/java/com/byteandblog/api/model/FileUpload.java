package com.byteandblog.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "file_uploads")
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "stored_name", nullable = false)
    private String storedName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Default constructor
    public FileUpload() {
    }

    // Constructor with all fields
    public FileUpload(Long id, String originalName, String storedName, String filePath,
                     String fileType, Long fileSize, String url, User user, LocalDateTime createdAt) {
        this.id = id;
        this.originalName = originalName;
        this.storedName = storedName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.url = url;
        this.user = user;
        this.createdAt = createdAt;
    }

    // Builder pattern method
    public static FileUploadBuilder builder() {
        return new FileUploadBuilder();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getStoredName() {
        return storedName;
    }

    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Builder class
    public static class FileUploadBuilder {
        private Long id;
        private String originalName;
        private String storedName;
        private String filePath;
        private String fileType;
        private Long fileSize;
        private String url;
        private User user;
        private LocalDateTime createdAt;

        FileUploadBuilder() {
        }

        public FileUploadBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FileUploadBuilder originalName(String originalName) {
            this.originalName = originalName;
            return this;
        }

        public FileUploadBuilder storedName(String storedName) {
            this.storedName = storedName;
            return this;
        }

        public FileUploadBuilder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public FileUploadBuilder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public FileUploadBuilder fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public FileUploadBuilder url(String url) {
            this.url = url;
            return this;
        }

        public FileUploadBuilder user(User user) {
            this.user = user;
            return this;
        }

        public FileUploadBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FileUpload build() {
            return new FileUpload(id, originalName, storedName, filePath, fileType, fileSize, url, user, createdAt);
        }
    }
}
