package com.byteandblog.api.controller;

import com.byteandblog.api.dto.ProfileImageResponse;
import com.byteandblog.api.dto.UserProfileDto;
import com.byteandblog.api.dto.UserProfileUpdateRequest;
import com.byteandblog.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {
    
    private static final Logger logger = Logger.getLogger(UserProfileController.class.getName());
    
    private final UserService userService;
    
    public UserProfileController(UserService userService) {
        this.userService = userService;
        logger.info("UserProfileController initialized with UserService");
    }
    
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileDto> getUserProfile(Authentication authentication) {
        logger.info("GET /api/users/profile request received");
        String email = authentication.getName();
        UserProfileDto profile = userService.getUserProfile(email);
        return ResponseEntity.ok(profile);
    }
    
    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileDto> updateUserProfile(
            @Valid @RequestBody UserProfileUpdateRequest request,
            Authentication authentication) {
        logger.info("PUT /api/users/profile request received");
        String email = authentication.getName();
        UserProfileDto updatedProfile = userService.updateUserProfile(email, request);
        return ResponseEntity.ok(updatedProfile);
    }
    
    @PostMapping("/profile/picture")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileImageResponse> uploadProfilePicture(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        logger.info("POST /api/users/profile/picture request received");
        String email = authentication.getName();
        String imageUrl = userService.uploadProfilePicture(email, file);
        
        ProfileImageResponse response = ProfileImageResponse.builder()
                .imageUrl(imageUrl)
                .build();
        
        return ResponseEntity.ok(response);
    }
    
    
}
