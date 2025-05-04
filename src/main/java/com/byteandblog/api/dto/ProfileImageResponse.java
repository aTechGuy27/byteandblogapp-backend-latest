package com.byteandblog.api.dto;

public class ProfileImageResponse {
    private String imageUrl;

    // Default constructor
    public ProfileImageResponse() {
    }

    // All-args constructor
    public ProfileImageResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    // Getters and setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Builder class
    public static class Builder {
        private String imageUrl;

        private Builder() {
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ProfileImageResponse build() {
            return new ProfileImageResponse(imageUrl);
        }
    }
}
