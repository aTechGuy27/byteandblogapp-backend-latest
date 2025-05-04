package com.byteandblog.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.byteandblog.api.dto.BlogPostDto;
import com.byteandblog.api.dto.BlogPostRequest;
import com.byteandblog.api.dto.PageResponse;
import com.byteandblog.api.exception.ResourceNotFoundException;
import com.byteandblog.api.model.BlogPost;
import com.byteandblog.api.model.User;
import com.byteandblog.api.repository.BlogPostRepository;

@Service
public class BlogPostService {
	
	private static final Logger log = LoggerFactory.getLogger(BlogPostService.class);

    private final BlogPostRepository blogPostRepository;
    private final UserService userService;
    
    

    public BlogPostService(BlogPostRepository blogPostRepository, UserService userService) {
		this.blogPostRepository = blogPostRepository;
		this.userService = userService;
	}

    public Page<BlogPostDto> getAllBlogPosts(int page, int size) {
        log.debug("Getting all blog posts, page: {}, size: {}", page, size);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogPost> blogPosts = blogPostRepository.findAllByOrderByPublishedAtDesc(pageable);
        
        return blogPosts.map(BlogPostDto::fromEntity);
    }

    public BlogPostDto getBlogPostById(Long id) {
        log.debug("Getting blog post by id: {}", id);
        
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Blog post not found with id: {}", id);
                    return new ResourceNotFoundException("Blog post not found with id: " + id);
                });
        
        return BlogPostDto.fromEntity(blogPost);
    }

    public List<BlogPostDto> getFeaturedBlogPosts(int limit) {
        log.debug("Getting featured blog posts, limit: {}", limit);
        
        List<BlogPost> blogPosts = blogPostRepository.findTopByOrderByPublishedAtDesc(limit);
        
        return blogPosts.stream()
                .map(BlogPostDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public BlogPostDto createBlogPost(BlogPostRequest request, String userEmail) {
        log.info("Creating new blog post: {}", request.getTitle());
        
        User user = userService.getUserEntityByEmail(userEmail);
        
        BlogPost blogPost = BlogPost.builder()
                .title(request.getTitle())
                .excerpt(request.getExcerpt())
                .content(request.getContent())
                .coverImage(request.getCoverImage())
                .coverImageCaption(request.getCoverImageCaption())
                .tags(request.getTags())
                .author(user)
                .publishedAt(LocalDateTime.now())
                .build();
        
        blogPost = blogPostRepository.save(blogPost);
        log.info("Blog post created successfully with id: {}", blogPost.getId());
        
        return BlogPostDto.fromEntity(blogPost);
    }

    @Transactional
    public BlogPostDto updateBlogPost(Long id, BlogPostRequest request, String userEmail) {
        log.info("Updating blog post with id: {}", id);
        
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Blog post not found with id: {}", id);
                    return new ResourceNotFoundException("Blog post not found with id: " + id);
                });
        
        User user = userService.getUserEntityByEmail(userEmail);
        
        blogPost.setTitle(request.getTitle());
        blogPost.setExcerpt(request.getExcerpt());
        blogPost.setContent(request.getContent());
        blogPost.setCoverImage(request.getCoverImage());
        blogPost.setCoverImageCaption(request.getCoverImageCaption());
        blogPost.setTags(request.getTags());
        
        blogPost = blogPostRepository.save(blogPost);
        log.info("Blog post updated successfully: {}", blogPost.getId());
        
        return BlogPostDto.fromEntity(blogPost);
    }

    @Transactional
    public void deleteBlogPost(Long id) {
        log.info("Deleting blog post with id: {}", id);
        
        if (!blogPostRepository.existsById(id)) {
            log.warn("Blog post not found with id: {}", id);
            throw new ResourceNotFoundException("Blog post not found with id: " + id);
        }
        
        blogPostRepository.deleteById(id);
        log.info("Blog post deleted successfully: {}", id);
    }

    public List<BlogPostDto> getAllBlogPostsForAdmin() {
        log.debug("Getting all blog posts for admin");
        
        List<BlogPost> blogPosts = blogPostRepository.findAll();
        
        return blogPosts.stream()
                .map(BlogPostDto::fromEntity)
                .collect(Collectors.toList());
    }
}
