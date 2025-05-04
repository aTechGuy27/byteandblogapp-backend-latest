package com.byteandblog.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byteandblog.api.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByBlogPostIdOrderByCreatedAtDesc(Long blogPostId);
    
    Page<Comment> findByBlogPostIdOrderByCreatedAtDesc(Long blogPostId, Pageable pageable);
    
    List<Comment> findByAuthorIdOrderByCreatedAtDesc(Long authorId);
    
    // New methods for dashboard stats
    long countByCreatedAtAfter(LocalDateTime date);
    
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    // New method for recent comments
    List<Comment> findTop10ByOrderByCreatedAtDesc();
    
    // Method with custom limit
    List<Comment> findByOrderByCreatedAtDesc(Pageable pageable);
}
