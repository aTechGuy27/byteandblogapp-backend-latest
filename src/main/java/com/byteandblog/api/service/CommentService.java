package com.byteandblog.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.byteandblog.api.dto.CommentDto;
import com.byteandblog.api.dto.CommentRequest;
import com.byteandblog.api.dto.RecentCommentDto;
import com.byteandblog.api.exception.ResourceNotFoundException;
import com.byteandblog.api.exception.UnauthorizedException;
import com.byteandblog.api.model.BlogPost;
import com.byteandblog.api.model.Comment;
import com.byteandblog.api.model.User;
import com.byteandblog.api.repository.BlogPostRepository;
import com.byteandblog.api.repository.CommentRepository;

@Service
public class CommentService {
	
	private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final BlogPostRepository blogPostRepository;
    private final UserService userService;
    
    

    public CommentService(CommentRepository commentRepository, BlogPostRepository blogPostRepository,
			UserService userService) {
		this.commentRepository = commentRepository;
		this.blogPostRepository = blogPostRepository;
		this.userService = userService;
	}

	public List<CommentDto> getCommentsByPostId(Long postId) {
        log.debug("Getting comments for post id: {}", postId);
        
        List<Comment> comments = commentRepository.findByBlogPostIdOrderByCreatedAtDesc(postId);
        
        return comments.stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto addComment(CommentRequest request, String userEmail) {
        log.info("Adding comment to post id: {}", request.getPostId());
        
        User user = userService.getUserEntityByEmail(userEmail);
        
        BlogPost blogPost = blogPostRepository.findById(request.getPostId())
                .orElseThrow(() -> {
                    log.warn("Blog post not found with id: {}", request.getPostId());
                    return new ResourceNotFoundException("Blog post not found with id: " + request.getPostId());
                });
        
        Comment comment = Comment.builder()
                .content(request.getContent())
                .blogPost(blogPost)
                .author(user)
                .build();
        
        comment = commentRepository.save(comment);
        log.info("Comment added successfully with id: {}", comment.getId());
        
        return CommentDto.fromEntity(comment);
    }

    @Transactional
    public void deleteComment(Long id, String userEmail) {
        log.info("Deleting comment with id: {}", id);
        
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Comment not found with id: {}", id);
                    return new ResourceNotFoundException("Comment not found with id: " + id);
                });
        
        User user = userService.getUserEntityByEmail(userEmail);
        
        // Check if the user is the author of the comment or an admin
        // Updated to use String comparison instead of enum
        if (!comment.getAuthor().getId().equals(user.getId()) && !"ADMIN".equals(user.getRole())) {
            log.warn("User {} is not authorized to delete comment {}", user.getEmail(), id);
            throw new UnauthorizedException("You are not authorized to delete this comment");
        }
        
        commentRepository.deleteById(id);
        log.info("Comment deleted successfully: {}", id);
    }
    
    /**
     * Get recent comments across all blog posts
     * 
     * @param limit Maximum number of comments to return
     * @return List of recent comments with blog post titles
     */
    public List<RecentCommentDto> getRecentComments(int limit) {
        log.debug("Getting {} recent comments", limit);
        
        // Ensure limit is reasonable
        int validLimit = Math.min(Math.max(limit, 1), 50);
        
        Pageable pageable = PageRequest.of(0, validLimit);
        List<Comment> comments = commentRepository.findByOrderByCreatedAtDesc(pageable);
        
        return comments.stream()
                .map(RecentCommentDto::fromEntity)
                .collect(Collectors.toList());
    }
}
