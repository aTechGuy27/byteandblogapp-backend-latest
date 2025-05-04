package com.byteandblog.api.service;

import com.byteandblog.api.dto.DashboardStatsDto;
import com.byteandblog.api.repository.BlogPostRepository;
import com.byteandblog.api.repository.CommentRepository;
import com.byteandblog.api.repository.FileUploadRepository;
import com.byteandblog.api.repository.PortfolioItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class DashboardService {
    private static final Logger logger = Logger.getLogger(DashboardService.class.getName());
    
    private final BlogPostRepository blogPostRepository;
    private final CommentRepository commentRepository;
    private final PortfolioItemRepository portfolioItemRepository;
    private final FileUploadRepository fileUploadRepository;

    public DashboardService(
            BlogPostRepository blogPostRepository,
            CommentRepository commentRepository,
            PortfolioItemRepository portfolioItemRepository,
            FileUploadRepository fileUploadRepository) {
        this.blogPostRepository = blogPostRepository;
        this.commentRepository = commentRepository;
        this.portfolioItemRepository = portfolioItemRepository;
        this.fileUploadRepository = fileUploadRepository;
        logger.info("DashboardService initialized");
    }

    public DashboardStatsDto getDashboardStats() {
        logger.info("Fetching dashboard statistics");
        
        // Get total counts
        long totalPosts = blogPostRepository.count();
        long totalComments = commentRepository.count();
        long totalPortfolioItems = portfolioItemRepository.count();
        long totalUploads = fileUploadRepository.count();
        
        // Calculate growth metrics (comparing current month to previous month)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = now.minusMonths(1);
        LocalDateTime twoMonthsAgo = now.minusMonths(2);
        
        // Get counts for current month and previous month
        long currentMonthPosts = blogPostRepository.countByPublishedAtAfter(oneMonthAgo);
        long previousMonthPosts = blogPostRepository.countByPublishedAtBetween(twoMonthsAgo, oneMonthAgo);
        
        long currentMonthComments = commentRepository.countByCreatedAtAfter(oneMonthAgo);
        long previousMonthComments = commentRepository.countByCreatedAtBetween(twoMonthsAgo, oneMonthAgo);
        
        long currentMonthPortfolio = portfolioItemRepository.countByCreatedAtAfter(oneMonthAgo);
        long previousMonthPortfolio = portfolioItemRepository.countByCreatedAtBetween(twoMonthsAgo, oneMonthAgo);
        
        long currentMonthUploads = fileUploadRepository.countByCreatedAtAfter(oneMonthAgo);
        long previousMonthUploads = fileUploadRepository.countByCreatedAtBetween(twoMonthsAgo, oneMonthAgo);
        
        // Calculate growth percentages
        double postsGrowth = calculateGrowthPercentage(previousMonthPosts, currentMonthPosts);
        double commentsGrowth = calculateGrowthPercentage(previousMonthComments, currentMonthComments);
        double portfolioGrowth = calculateGrowthPercentage(previousMonthPortfolio, currentMonthPortfolio);
        double uploadsGrowth = calculateGrowthPercentage(previousMonthUploads, currentMonthUploads);
        
        logger.info("Dashboard stats calculated successfully");
        
        return DashboardStatsDto.builder()
                .totalPosts(totalPosts)
                .totalComments(totalComments)
                .totalPortfolioItems(totalPortfolioItems)
                .totalUploads(totalUploads)
                .recentPostsGrowth(postsGrowth)
                .recentCommentsGrowth(commentsGrowth)
                .recentPortfolioGrowth(portfolioGrowth)
                .recentUploadsGrowth(uploadsGrowth)
                .build();
    }
    
    private double calculateGrowthPercentage(long previous, long current) {
        if (previous == 0) {
            return current > 0 ? 100.0 : 0.0;
        }
        return ((double) (current - previous) / previous) * 100.0;
    }
}
