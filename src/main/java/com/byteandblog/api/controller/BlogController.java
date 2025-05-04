package com.byteandblog.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
import org.springframework.data.web.PagedResourcesAssembler;

import com.byteandblog.api.dto.BlogPostDto;
import com.byteandblog.api.dto.BlogPostRequest;
import com.byteandblog.api.service.BlogPostService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
	
	private static final Logger log = LoggerFactory.getLogger(BlogController.class);

    private final BlogPostService blogPostService;
    private final PagedResourcesAssembler<BlogPostDto> pagedResourcesAssembler;
    
    
    public BlogController(BlogPostService blogPostService,
			PagedResourcesAssembler<BlogPostDto> pagedResourcesAssembler) {
		this.blogPostService = blogPostService;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
	}

	@GetMapping
    public ResponseEntity<PagedModel<EntityModel<BlogPostDto>>> getAllBlogPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting all blog posts, page: {}, size: {}", page, size);
        
        Page<BlogPostDto> blogPostPage = blogPostService.getAllBlogPosts(page, size);
        
        PagedModel<EntityModel<BlogPostDto>> pagedModel = pagedResourcesAssembler.toModel(
                blogPostPage,
                dto -> EntityModel.of(dto, 
                        linkTo(methodOn(BlogController.class).getBlogPostById(dto.getId())).withSelfRel())
        );
        
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BlogPostDto>> getBlogPostById(@PathVariable Long id) {
        log.info("Getting blog post by id: {}", id);
        
        BlogPostDto blogPost = blogPostService.getBlogPostById(id);
        
        EntityModel<BlogPostDto> entityModel = EntityModel.of(blogPost,
                linkTo(methodOn(BlogController.class).getBlogPostById(id)).withSelfRel(),
                linkTo(methodOn(BlogController.class).getAllBlogPosts(0, 10)).withRel("all-blog-posts"));
        
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<BlogPostDto>> getFeaturedBlogPosts(
            @RequestParam(defaultValue = "3") int limit) {
        log.info("Getting featured blog posts, limit: {}", limit);
        return ResponseEntity.ok(blogPostService.getFeaturedBlogPosts(limit));
    }

    @PostMapping
    public ResponseEntity<EntityModel<BlogPostDto>> createBlogPost(
            @Valid @RequestBody BlogPostRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Creating new blog post: {}", request.getTitle());
        
        BlogPostDto createdPost = blogPostService.createBlogPost(request, userDetails.getUsername());
        
        EntityModel<BlogPostDto> entityModel = EntityModel.of(createdPost,
                linkTo(methodOn(BlogController.class).getBlogPostById(createdPost.getId())).withSelfRel(),
                linkTo(methodOn(BlogController.class).getAllBlogPosts(0, 10)).withRel("all-blog-posts"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BlogPostDto>> updateBlogPost(
            @PathVariable Long id,
            @Valid @RequestBody BlogPostRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Updating blog post with id: {}", id);
        
        BlogPostDto updatedPost = blogPostService.updateBlogPost(id, request, userDetails.getUsername());
        
        EntityModel<BlogPostDto> entityModel = EntityModel.of(updatedPost,
                linkTo(methodOn(BlogController.class).getBlogPostById(id)).withSelfRel(),
                linkTo(methodOn(BlogController.class).getAllBlogPosts(0, 10)).withRel("all-blog-posts"));
        
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable Long id) {
        log.info("Deleting blog post with id: {}", id);
        blogPostService.deleteBlogPost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<BlogPostDto>> getAllBlogPostsForAdmin() {
        log.info("Getting all blog posts for admin");
        return ResponseEntity.ok(blogPostService.getAllBlogPostsForAdmin());
    }
}
