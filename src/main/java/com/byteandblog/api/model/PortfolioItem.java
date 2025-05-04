package com.byteandblog.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "portfolio_items")
public class PortfolioItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private String image;

    @ElementCollection
    @CollectionTable(name = "portfolio_item_technologies", joinColumns = @JoinColumn(name = "portfolio_item_id"))
    @Column(name = "technology")
    private List<String> technologies = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "portfolio_item_features", joinColumns = @JoinColumn(name = "portfolio_item_id"))
    @Column(name = "feature", columnDefinition = "TEXT")
    private List<String> features = new ArrayList<>();

    @Column(name = "live_url")
    private String liveUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(columnDefinition = "TEXT")
    private String challenge;

    @Column(columnDefinition = "TEXT")
    private String solution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor
    public PortfolioItem() {
    }

    // Constructor with all fields
    public PortfolioItem(Long id, String title, String description, String content, String image,
                        List<String> technologies, List<String> features, String liveUrl,
                        String githubUrl, String challenge, String solution, User user,
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

    // Builder pattern method
    public static PortfolioItemBuilder builder() {
        return new PortfolioItemBuilder();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Builder class
    public static class PortfolioItemBuilder {
        private Long id;
        private String title;
        private String description;
        private String content;
        private String image;
        private List<String> technologies = new ArrayList<>();
        private List<String> features = new ArrayList<>();
        private String liveUrl;
        private String githubUrl;
        private String challenge;
        private String solution;
        private User user;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        PortfolioItemBuilder() {
        }

        public PortfolioItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PortfolioItemBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PortfolioItemBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PortfolioItemBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PortfolioItemBuilder image(String image) {
            this.image = image;
            return this;
        }

        public PortfolioItemBuilder technologies(List<String> technologies) {
            this.technologies = technologies;
            return this;
        }

        public PortfolioItemBuilder features(List<String> features) {
            this.features = features;
            return this;
        }

        public PortfolioItemBuilder liveUrl(String liveUrl) {
            this.liveUrl = liveUrl;
            return this;
        }

        public PortfolioItemBuilder githubUrl(String githubUrl) {
            this.githubUrl = githubUrl;
            return this;
        }

        public PortfolioItemBuilder challenge(String challenge) {
            this.challenge = challenge;
            return this;
        }

        public PortfolioItemBuilder solution(String solution) {
            this.solution = solution;
            return this;
        }

        public PortfolioItemBuilder user(User user) {
            this.user = user;
            return this;
        }

        public PortfolioItemBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PortfolioItemBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PortfolioItem build() {
            return new PortfolioItem(id, title, description, content, image, technologies, features,
                                    liveUrl, githubUrl, challenge, solution, user, createdAt, updatedAt);
        }
    }
}
