package com.byteandblog.api.service;

import com.byteandblog.api.dto.UserDto;
import com.byteandblog.api.dto.UserProfileDto;
import com.byteandblog.api.dto.UserProfileUpdateRequest;
import com.byteandblog.api.exception.BadRequestException;
import com.byteandblog.api.exception.ResourceNotFoundException;
import com.byteandblog.api.model.User;
import com.byteandblog.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private static final Set<String> ALLOWED_IMAGE_TYPES = new HashSet<>(Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    ));
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public UserService(UserRepository userRepository, FileStorageService fileStorageService) {
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        logger.info("UserService initialized with UserRepository and FileStorageService");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by username: " + username);
        return userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    logger.warning("User not found with email: " + username);
                    return new UsernameNotFoundException("User not found with email: " + username);
                });
    }

    public UserDto getUserById(Long id) {
        logger.info("Getting user by id: " + id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warning("User not found with id: " + id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });
        return UserDto.fromEntity(user);
    }

    public User getUserEntityById(Long id) {
        logger.info("Getting user entity by id: " + id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warning("User not found with id: " + id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });
    }

    public User getUserEntityByEmail(String email) {
        logger.info("Getting user entity by email: " + email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warning("User not found with email: " + email);
                    return new ResourceNotFoundException("User not found with email: " + email);
                });
    }
    
    public UserProfileDto getUserProfile(String email) {
        logger.info("Getting user profile for email: " + email);
        User user = getUserEntityByEmail(email);
        return UserProfileDto.fromEntity(user);
    }
    
    public UserProfileDto updateUserProfile(String email, UserProfileUpdateRequest request) {
        logger.info("Updating user profile for email: " + email);
        User user = getUserEntityByEmail(email);
        
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        
        if (request.getAge() != null) {
            user.setAge(request.getAge());
        }
        
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
        
        user = userRepository.save(user);
        logger.info("User profile updated for email: " + email);
        
        return UserProfileDto.fromEntity(user);
    }
    
    public String uploadProfilePicture(String email, MultipartFile file) {
        logger.info("Uploading profile picture for email: " + email);
        
        // Validate file
        if (file.isEmpty()) {
            throw new BadRequestException("File cannot be empty");
        }
        
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("File size exceeds maximum limit of 5MB");
        }
        
        if (!ALLOWED_IMAGE_TYPES.contains(file.getContentType())) {
            throw new BadRequestException("Only image files (JPEG, PNG, GIF, WebP) are allowed");
        }
        
        // Get user
        User user = getUserEntityByEmail(email);
        
        // Store file
        String imageUrl = fileStorageService.storeFile(file, "profile");
        
        // Update user
        user.setProfileImage(imageUrl);
        userRepository.save(user);
        
        logger.info("Profile picture uploaded for email: " + email + ", URL: " + imageUrl);
        
        return imageUrl;
    }
    
    public void updateLastLogin(String email) {
        logger.info("Updating last login for email: " + email);
        User user = getUserEntityByEmail(email);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        logger.info("Last login updated for email: " + email);
    }
    
    
}
