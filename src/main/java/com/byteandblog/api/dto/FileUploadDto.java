package com.byteandblog.api.dto;

import com.byteandblog.api.model.FileUpload;

import java.time.LocalDateTime;

public class FileUploadDto {
    private Long id;
    private String originalName;
    private String fileType;
    private Long fileSize;
    private String url;
    private LocalDateTime createdAt;

    // Default constructor
    public FileUploadDto() {
    }

    // Constructor with all fields
    public FileUploadDto(Long id, String originalName, String fileType, Long fileSize, 
                        String url, LocalDateTime createdAt) {
        this.id = id;
        this.originalName = originalName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.url = url;
        this.createdAt = createdAt;
    }

    // Static factory method to create from entity
    public static FileUploadDto fromEntity(FileUpload fileUpload) {
        return new FileUploadDtoBuilder()
                .id(fileUpload.getId())
                .originalName(fileUpload.getOriginalName())
                .fileType(fileUpload.getFileType())
                .fileSize(fileUpload.getFileSize())
                .url(fileUpload.getUrl())
                .createdAt(fileUpload.getCreatedAt())
                .build();
    }

    // Builder pattern method
    public static FileUploadDtoBuilder builder() {
        return new FileUploadDtoBuilder();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Builder class
    public static class FileUploadDtoBuilder {
        private Long id;
        private String originalName;
        private String fileType;
        private Long fileSize;
        private String url;
        private LocalDateTime createdAt;

        FileUploadDtoBuilder() {
        }

        public FileUploadDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FileUploadDtoBuilder originalName(String originalName) {
            this.originalName = originalName;
            return this;
        }

        public FileUploadDtoBuilder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public FileUploadDtoBuilder fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public FileUploadDtoBuilder url(String url) {
            this.url = url;
            return this;
        }

        public FileUploadDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FileUploadDto build() {
            return new FileUploadDto(id, originalName, fileType, fileSize, url, createdAt);
        }
    }
}
