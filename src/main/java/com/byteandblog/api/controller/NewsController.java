package com.byteandblog.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.byteandblog.api.dto.NewsResponseDto;
import com.byteandblog.api.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {

	private static final Logger log = LoggerFactory.getLogger(NewsController.class);
	
    private final NewsService newsService;
    
    

    public NewsController(NewsService newsService) {
		this.newsService = newsService;
	}

    @GetMapping("")
    public ResponseEntity<String> getNewsRoot() {
        return ResponseEntity.ok("News API is running. Use /api/news/top-headlines to get news.");
    }

    @GetMapping("/top-headlines")
    public ResponseEntity<NewsResponseDto> getTopHeadlines(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String category) {
        
        log.info("Getting top headlines, page: {}, pageSize: {}, category: {}", page, pageSize, category);
        
        NewsResponseDto response;
        if (category != null && !category.isEmpty()) {
            // Map category names to NPR feed IDs
            String feedId = mapCategoryToFeedId(category);
            response = newsService.getTopHeadlines(page, pageSize, feedId);
        } else {
            response = newsService.getTopHeadlines(page, pageSize);
        }
        
        return ResponseEntity.ok(response);
    }
    
    private String mapCategoryToFeedId(String category) {
        return switch (category.toLowerCase()) {
            case "world" -> "1003";
            case "business" -> "1004";
            case "politics" -> "1006";
            case "science" -> "1007";
            case "health" -> "1008";
            case "education" -> "1013";
            case "technology" -> "1019";
            case "economy" -> "1032";
            default -> "1001"; // Default to national news
        };
    }
}
