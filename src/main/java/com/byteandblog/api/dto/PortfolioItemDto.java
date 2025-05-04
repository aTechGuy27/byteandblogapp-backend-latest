package com.byteandblog.api.dto;

import com.byteandblog.api.model.PortfolioItem;

import java.time.LocalDateTime;
import java.util.List;

public class PortfolioItemDto {
    private Long id;
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
    private UserDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public PortfolioItemDto() {
    }

    // Constructor with all fields
    public PortfolioItemDto(Long id, String title, String description, String content, String image,
                           List<String> technologies, List<String> features, String liveUrl,
                           String githubUrl, String challenge, String solution, UserDto user,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
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
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Static factory method to create from entity
    public static PortfolioItemDto fromEntity(PortfolioItem item) {
        return new PortfolioItemDtoBuilder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .content(item.getContent())
                .image(item.getImage())
                .technologies(item.getTechnologies())
                .features(item.getFeatures())
                .liveUrl(item.getLiveUrl())
                .githubUrl(item.getGithubUrl())
                .challenge(item.getChallenge())
                .solution(item.getSolution())
                .user(UserDto.fromEntity(item.getUser()))
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    // Builder pattern method
    public static PortfolioItemDtoBuilder builder() {
        return new PortfolioItemDtoBuilder();
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
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
    public static class PortfolioItemDtoBuilder {
        private Long id;
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
        private UserDto user;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        PortfolioItemDtoBuilder() {
        }

        public PortfolioItemDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PortfolioItemDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PortfolioItemDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PortfolioItemDtoBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PortfolioItemDtoBuilder image(String image) {
            this.image = image;
            return this;
        }

        public PortfolioItemDtoBuilder technologies(List<String> technologies) {
            this.technologies = technologies;
            return this;
        }

        public PortfolioItemDtoBuilder features(List<String> features) {
            this.features = features;
            return this;
        }

        public PortfolioItemDtoBuilder liveUrl(String liveUrl) {
            this.liveUrl = liveUrl;
            return this;
        }

        public PortfolioItemDtoBuilder githubUrl(String githubUrl) {
            this.githubUrl = githubUrl;
            return this;
        }

        public PortfolioItemDtoBuilder challenge(String challenge) {
            this.challenge = challenge;
            return this;
        }

        public PortfolioItemDtoBuilder solution(String solution) {
            this.solution = solution;
            return this;
        }

        public PortfolioItemDtoBuilder user(UserDto user) {
            this.user = user;
            return this;
        }

        public PortfolioItemDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PortfolioItemDtoBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PortfolioItemDto build() {
            return new PortfolioItemDto(id, title, description, content, image, technologies, features,
                                       liveUrl, githubUrl, challenge, solution, user, createdAt, updatedAt);
        }
    }
}
