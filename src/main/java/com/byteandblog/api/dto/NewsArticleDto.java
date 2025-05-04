package com.byteandblog.api.dto;

import java.time.LocalDateTime;

public class NewsArticleDto {
    private String title;
    private String description;
    private String url;
    private LocalDateTime publishedAt;
    private NewsSourceDto source;
    private String imageUrl;

    // Default constructor
    public NewsArticleDto() {
    }

    // Constructor with all fields
    public NewsArticleDto(String title, String description, String url, 
                         LocalDateTime publishedAt, NewsSourceDto source,String imageUrl) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
        this.source = source;
        this.imageUrl=imageUrl;
    }

    // Builder pattern method
    public static NewsArticleDtoBuilder builder() {
        return new NewsArticleDtoBuilder();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public NewsSourceDto getSource() {
        return source;
    }

    public void setSource(NewsSourceDto source) {
        this.source = source;
    }
    
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
	
	// Builder class
    public static class NewsArticleDtoBuilder {
        private String title;
        private String description;
        private String url;
        private LocalDateTime publishedAt;
        private NewsSourceDto source;
        private String imageUrl;

        NewsArticleDtoBuilder() {
        }

        public NewsArticleDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public NewsArticleDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public NewsArticleDtoBuilder url(String url) {
            this.url = url;
            return this;
        }

        public NewsArticleDtoBuilder publishedAt(LocalDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public NewsArticleDtoBuilder source(NewsSourceDto source) {
            this.source = source;
            return this;
        }
        
        public NewsArticleDtoBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }
        

        public NewsArticleDto build() {
            return new NewsArticleDto(title, description, url, publishedAt, source,imageUrl);
        }
    }

    // Inner class for NewsSourceDto
    public static class NewsSourceDto {
        private String name;

        // Default constructor
        public NewsSourceDto() {
        }

        // Constructor with all fields
        public NewsSourceDto(String name) {
            this.name = name;
        }

        // Builder pattern method
        public static NewsSourceDtoBuilder builder() {
            return new NewsSourceDtoBuilder();
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // Builder class
        public static class NewsSourceDtoBuilder {
            private String name;

            NewsSourceDtoBuilder() {
            }

            public NewsSourceDtoBuilder name(String name) {
                this.name = name;
                return this;
            }

            public NewsSourceDto build() {
                return new NewsSourceDto(name);
            }
        }
    }
}
