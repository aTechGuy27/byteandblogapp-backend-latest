package com.byteandblog.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.byteandblog.api.dto.PortfolioItemDto;
import com.byteandblog.api.dto.PortfolioItemRequest;
import com.byteandblog.api.service.PortfolioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

	private static final Logger log = LoggerFactory.getLogger(PortfolioController.class);
	
    private final PortfolioService portfolioService;
    
    

    public PortfolioController(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	@GetMapping
    public ResponseEntity<List<PortfolioItemDto>> getAllPortfolioItems() {
        log.info("Getting all portfolio items");
        return ResponseEntity.ok(portfolioService.getAllPortfolioItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioItemDto> getPortfolioItemById(@PathVariable Long id) {
        log.info("Getting portfolio item by id: {}", id);
        return ResponseEntity.ok(portfolioService.getPortfolioItemById(id));
    }

    @GetMapping("/highlights")
    public ResponseEntity<List<PortfolioItemDto>> getPortfolioHighlights(
            @RequestParam(defaultValue = "3") int limit) {
        log.info("Getting portfolio highlights, limit: {}", limit);
        return ResponseEntity.ok(portfolioService.getPortfolioHighlights(limit));
    }

    @PostMapping
    public ResponseEntity<PortfolioItemDto> createPortfolioItem(
            @Valid @RequestBody PortfolioItemRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Creating new portfolio item: {}", request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(portfolioService.createPortfolioItem(request, userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortfolioItemDto> updatePortfolioItem(
            @PathVariable Long id,
            @Valid @RequestBody PortfolioItemRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Updating portfolio item with id: {}", id);
        return ResponseEntity.ok(portfolioService.updatePortfolioItem(id, request, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolioItem(@PathVariable Long id) {
        log.info("Deleting portfolio item with id: {}", id);
        portfolioService.deletePortfolioItem(id);
        return ResponseEntity.noContent().build();
    }
}
