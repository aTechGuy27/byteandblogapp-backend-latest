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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.byteandblog.api.dto.CommentDto;
import com.byteandblog.api.dto.CommentRequest;
import com.byteandblog.api.dto.RecentCommentDto;
import com.byteandblog.api.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	private static final Logger log = LoggerFactory.getLogger(CommentController.class);
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping
	public ResponseEntity<List<CommentDto>> getCommentsByPostId(@RequestParam Long postId) {
		log.info("Getting comments for post id: {}", postId);
		return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
	}

	@PostMapping
	public ResponseEntity<CommentDto> addComment(@Valid @RequestBody CommentRequest request,
			@AuthenticationPrincipal UserDetails userDetails) {
		log.info("Adding comment to post id: {}", request.getPostId());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(commentService.addComment(request, userDetails.getUsername()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
		log.info("Deleting comment with id: {}", id);
		commentService.deleteComment(id, userDetails.getUsername());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/recent")
	public ResponseEntity<List<RecentCommentDto>> getRecentComments(@RequestParam(defaultValue = "5") int limit) {
		log.info("Getting {} recent comments", limit);
		return ResponseEntity.ok(commentService.getRecentComments(limit));
	}
}
