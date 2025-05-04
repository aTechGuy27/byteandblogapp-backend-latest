package com.byteandblog.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.byteandblog.api.model.BlogPost;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

	@Query("SELECT b FROM BlogPost b JOIN b.tags t WHERE t = :tag ORDER BY b.publishedAt DESC")
	Page<BlogPost> findByTagOrderByPublishedAtDesc(String tag, Pageable pageable);

	@Query("SELECT b FROM BlogPost b WHERE b.author.id = :authorId ORDER BY b.publishedAt DESC")
	Page<BlogPost> findByAuthorIdOrderByPublishedAtDesc(Long authorId, Pageable pageable);
	
    Page<BlogPost> findAllByOrderByPublishedAtDesc(Pageable pageable);
    
    @Query(value = "SELECT * FROM blog_posts ORDER BY published_at DESC LIMIT ?1", nativeQuery = true)
    List<BlogPost> findTopByOrderByPublishedAtDesc(int limit);
    
    // New methods for dashboard stats
    long countByPublishedAtAfter(LocalDateTime date);
    
    long countByPublishedAtBetween(LocalDateTime start, LocalDateTime end);
}
