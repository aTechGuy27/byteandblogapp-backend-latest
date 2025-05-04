package com.byteandblog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class PortfolioItemRequest {
    
    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters")
    private String title;
    
    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;
    
    private String content;
    
    private String image;
    
    private List<String> technologies;
    
    private List<String> features;
    
    private String liveUrl;
    
    private String githubUrl;
    
    private String challenge;
    
    private String solution;

    // Default constructor
    public PortfolioItemRequest() {
    }

    // Constructor with all fields
    public PortfolioItemRequest(String title, String description, String content, String image,
                               List<String> technologies, List<String> features, String liveUrl,
                               String githubUrl, String challenge, String solution) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.image = image;
        this.technologies = technologies;
        this.features = features;
        this.liveUrl = liveUrl;
        this.githubUrl = githubUrl;
        this.challenge = challenge;
        this.solution = solution;
    }

    // Builder pattern method
    public static PortfolioItemRequestBuilder builder() {
        return new PortfolioItemRequestBuilder();
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    // Builder class
    public static class PortfolioItemRequestBuilder {
        private String title;
        private String description;
        private String content;
        private String image;
        private List<String> technologies;
        private List<String> features;
        private String liveUrl;
        private String githubUrl;
        private String challenge;
        private String solution;

        PortfolioItemRequestBuilder() {
        }

        public PortfolioItemRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PortfolioItemRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PortfolioItemRequestBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PortfolioItemRequestBuilder image(String image) {
            this.image = image;
            return this;
        }

        public PortfolioItemRequestBuilder technologies(List<String> technologies) {
            this.technologies = technologies;
            return this;
        }

        public PortfolioItemRequestBuilder features(List<String> features) {
            this.features = features;
            return this;
        }

        public PortfolioItemRequestBuilder liveUrl(String liveUrl) {
            this.liveUrl = liveUrl;
            return this;
        }

        public PortfolioItemRequestBuilder githubUrl(String githubUrl) {
            this.githubUrl = githubUrl;
            return this;
        }

        public PortfolioItemRequestBuilder challenge(String challenge) {
            this.challenge = challenge;
            return this;
        }

        public PortfolioItemRequestBuilder solution(String solution) {
            this.solution = solution;
            return this;
        }

        public PortfolioItemRequest build() {
            return new PortfolioItemRequest(title, description, content, image, technologies, features,
                                           liveUrl, githubUrl, challenge, solution);
        }
    }
}
