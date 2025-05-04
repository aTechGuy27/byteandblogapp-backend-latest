package com.byteandblog.api.dto;

public class DashboardStatsDto {
    private long totalPosts;
    private long totalComments;
    private long totalPortfolioItems;
    private long totalUploads;
    private double recentPostsGrowth;
    private double recentCommentsGrowth;
    private double recentPortfolioGrowth;
    private double recentUploadsGrowth;

    // Default constructor
    public DashboardStatsDto() {
    }

    // All-args constructor
    public DashboardStatsDto(long totalPosts, long totalComments, long totalPortfolioItems, long totalUploads,
                           double recentPostsGrowth, double recentCommentsGrowth, 
                           double recentPortfolioGrowth, double recentUploadsGrowth) {
        this.totalPosts = totalPosts;
        this.totalComments = totalComments;
        this.totalPortfolioItems = totalPortfolioItems;
        this.totalUploads = totalUploads;
        this.recentPostsGrowth = recentPostsGrowth;
        this.recentCommentsGrowth = recentCommentsGrowth;
        this.recentPortfolioGrowth = recentPortfolioGrowth;
        this.recentUploadsGrowth = recentUploadsGrowth;
    }

    // Getters and setters
    public long getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(long totalPosts) {
        this.totalPosts = totalPosts;
    }

    public long getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(long totalComments) {
        this.totalComments = totalComments;
    }

    public long getTotalPortfolioItems() {
        return totalPortfolioItems;
    }

    public void setTotalPortfolioItems(long totalPortfolioItems) {
        this.totalPortfolioItems = totalPortfolioItems;
    }

    public long getTotalUploads() {
        return totalUploads;
    }

    public void setTotalUploads(long totalUploads) {
        this.totalUploads = totalUploads;
    }

    public double getRecentPostsGrowth() {
        return recentPostsGrowth;
    }

    public void setRecentPostsGrowth(double recentPostsGrowth) {
        this.recentPostsGrowth = recentPostsGrowth;
    }

    public double getRecentCommentsGrowth() {
        return recentCommentsGrowth;
    }

    public void setRecentCommentsGrowth(double recentCommentsGrowth) {
        this.recentCommentsGrowth = recentCommentsGrowth;
    }

    public double getRecentPortfolioGrowth() {
        return recentPortfolioGrowth;
    }

    public void setRecentPortfolioGrowth(double recentPortfolioGrowth) {
        this.recentPortfolioGrowth = recentPortfolioGrowth;
    }

    public double getRecentUploadsGrowth() {
        return recentUploadsGrowth;
    }

    public void setRecentUploadsGrowth(double recentUploadsGrowth) {
        this.recentUploadsGrowth = recentUploadsGrowth;
    }

    // Manual Builder implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long totalPosts;
        private long totalComments;
        private long totalPortfolioItems;
        private long totalUploads;
        private double recentPostsGrowth;
        private double recentCommentsGrowth;
        private double recentPortfolioGrowth;
        private double recentUploadsGrowth;

        public Builder totalPosts(long totalPosts) {
            this.totalPosts = totalPosts;
            return this;
        }

        public Builder totalComments(long totalComments) {
            this.totalComments = totalComments;
            return this;
        }

        public Builder totalPortfolioItems(long totalPortfolioItems) {
            this.totalPortfolioItems = totalPortfolioItems;
            return this;
        }

        public Builder totalUploads(long totalUploads) {
            this.totalUploads = totalUploads;
            return this;
        }

        public Builder recentPostsGrowth(double recentPostsGrowth) {
            this.recentPostsGrowth = recentPostsGrowth;
            return this;
        }

        public Builder recentCommentsGrowth(double recentCommentsGrowth) {
            this.recentCommentsGrowth = recentCommentsGrowth;
            return this;
        }

        public Builder recentPortfolioGrowth(double recentPortfolioGrowth) {
            this.recentPortfolioGrowth = recentPortfolioGrowth;
            return this;
        }

        public Builder recentUploadsGrowth(double recentUploadsGrowth) {
            this.recentUploadsGrowth = recentUploadsGrowth;
            return this;
        }

        public DashboardStatsDto build() {
            return new DashboardStatsDto(
                totalPosts, 
                totalComments, 
                totalPortfolioItems, 
                totalUploads, 
                recentPostsGrowth, 
                recentCommentsGrowth, 
                recentPortfolioGrowth, 
                recentUploadsGrowth
            );
        }
    }

    @Override
    public String toString() {
        return "DashboardStatsDto{" +
                "totalPosts=" + totalPosts +
                ", totalComments=" + totalComments +
                ", totalPortfolioItems=" + totalPortfolioItems +
                ", totalUploads=" + totalUploads +
                ", recentPostsGrowth=" + recentPostsGrowth +
                ", recentCommentsGrowth=" + recentCommentsGrowth +
                ", recentPortfolioGrowth=" + recentPortfolioGrowth +
                ", recentUploadsGrowth=" + recentUploadsGrowth +
                '}';
    }
}
