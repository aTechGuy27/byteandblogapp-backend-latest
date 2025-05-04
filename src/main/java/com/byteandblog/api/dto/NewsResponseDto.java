package com.byteandblog.api.dto;

import java.util.List;

public class NewsResponseDto {
    private List<NewsArticleDto> articles;
    private int totalResults;

    // Default constructor
    public NewsResponseDto() {
    }

    // Constructor with all fields
    public NewsResponseDto(List<NewsArticleDto> articles, int totalResults) {
        this.articles = articles;
        this.totalResults = totalResults;
    }

    // Builder pattern method
    public static NewsResponseDtoBuilder builder() {
        return new NewsResponseDtoBuilder();
    }

    // Getters and Setters
    public List<NewsArticleDto> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsArticleDto> articles) {
        this.articles = articles;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    // Builder class
    public static class NewsResponseDtoBuilder {
        private List<NewsArticleDto> articles;
        private int totalResults;

        NewsResponseDtoBuilder() {
        }

        public NewsResponseDtoBuilder articles(List<NewsArticleDto> articles) {
            this.articles = articles;
            return this;
        }

        public NewsResponseDtoBuilder totalResults(int totalResults) {
            this.totalResults = totalResults;
            return this;
        }

        public NewsResponseDto build() {
            return new NewsResponseDto(articles, totalResults);
        }
    }
}
