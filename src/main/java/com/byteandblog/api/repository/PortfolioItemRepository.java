package com.byteandblog.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.byteandblog.api.model.PortfolioItem;

@Repository
public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, Long> {
    
    List<PortfolioItem> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    @Query(value = "SELECT * FROM portfolio_items ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<PortfolioItem> findTopByOrderByCreatedAtDesc(int limit);
    
 // New methods for dashboard stats
    long countByCreatedAtAfter(LocalDateTime date);
    
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
