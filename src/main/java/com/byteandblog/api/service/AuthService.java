package com.byteandblog.api.service;

import com.byteandblog.api.dto.*;
import com.byteandblog.api.exception.BadRequestException;
import com.byteandblog.api.exception.ResourceNotFoundException;
import com.byteandblog.api.model.User;
import com.byteandblog.api.repository.UserRepository;
import com.byteandblog.api.security.JwtService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, 
                      JwtService jwtService, AuthenticationManager authenticationManager,
                      EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        logger.info("AuthService initialized with dependencies");
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        logger.info("Registering new user with email: " + request.getEmail());
        
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warning("Email already in use: " + request.getEmail());
            throw new BadRequestException("Email already in use");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER") // Changed from User.Role.USER to "USER" string
                .createdAt(LocalDateTime.now())
                .build();

        user = userRepository.save(user);
        logger.info("User registered successfully with id: " + user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        
        String jwtToken = jwtService.generateToken(claims, user);
        
        return AuthResponse.builder()
                .token(jwtToken)
                .user(UserDto.fromEntity(user))
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        logger.info("Authenticating user with email: " + request.getEmail());
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.warning("User not found with email: " + request.getEmail());
                    return new ResourceNotFoundException("User not found with email: " + request.getEmail());
                });

        // Update last login time
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        
        String jwtToken = jwtService.generateToken(claims, user);
        logger.info("User authenticated successfully: " + user.getEmail());
        
        return AuthResponse.builder()
                .token(jwtToken)
                .user(UserDto.fromEntity(user))
                .build();
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        logger.info("Processing forgot password request for email: " + request.getEmail());
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            logger.warning("No user found with email: " + request.getEmail());
            // Don't reveal that the email doesn't exist for security reasons
            return;
        }

        // Generate a random token
        String resetToken = RandomStringUtils.randomAlphanumeric(64);
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        // Send email with reset link
        String resetLink = "https://byteandblog.com/auth/reset-password?token=" + resetToken;
        emailService.sendPasswordResetEmail(user.getEmail(), user.getName(), resetLink);
        
        logger.info("Password reset email sent to: " + user.getEmail());
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        logger.info("Processing password reset request");
        
        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> {
                    logger.warning("Invalid or expired reset token");
                    return new BadRequestException("Invalid or expired reset token");
                });

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            logger.warning("Reset token has expired");
            throw new BadRequestException("Reset token has expired");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
        
        logger.info("Password reset successfully for user: " + user.getEmail());
    }
}
