package com.byteandblog.api.controller;

import com.byteandblog.api.dto.ContactMessageDto;
import com.byteandblog.api.dto.ContactResponseDto;
import com.byteandblog.api.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final SpringTemplateEngine templateEngine;

    private static final Logger logger = Logger.getLogger(ContactController.class.getName());
    
    private final EmailService emailService;
    
    public ContactController(EmailService emailService, SpringTemplateEngine templateEngine) {
        this.emailService = emailService;
        this.templateEngine = templateEngine;
    }
    
    /**
     * Endpoint for handling contact form submissions
     * 
     * @param contactMessage The contact form data
     * @return Response indicating success or failure
     */
    @PostMapping
    public ResponseEntity<ContactResponseDto> submitContactForm(@Valid @RequestBody ContactMessageDto contactMessage) {
        logger.info("Received contact form submission from: " + contactMessage.toString());
        
        
        try {
            CompletableFuture<Boolean> emailFuture = emailService.sendContactFormEmail(
                contactMessage.getName(),
                contactMessage.getEmail(),
                contactMessage.getSubject(),
                contactMessage.getMessage()
            );
            
            // For simplicity, we'll wait for the result
            // In a real application, you might want to return immediately and handle the result asynchronously
            Boolean emailSent = emailFuture.get(); // This blocks until the future completes
            
            if (emailSent) {
                logger.info("Contact form email sent successfully");
                return ResponseEntity.ok(
                    ContactResponseDto.builder()
                        .success(true)
                        .message("Your message has been sent successfully. We'll get back to you soon!")
                        .build()
                );
            } else {
                logger.warning("Failed to send contact form email");
                return ResponseEntity.status(500).body(
                    ContactResponseDto.builder()
                        .success(false)
                        .message("Failed to send your message. Please try again later.")
                        .build()
                );
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.log(Level.SEVERE, "Error processing contact form submission: " + e.getMessage(), e);
            Thread.currentThread().interrupt(); // Reset interrupted status
            return ResponseEntity.status(500).body(
                ContactResponseDto.builder()
                    .success(false)
                    .message("An error occurred while processing your request. Please try again later.")
                    .build()
            );
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing contact form submission: " + e.getMessage(), e);
            return ResponseEntity.status(500).body(
                ContactResponseDto.builder()
                    .success(false)
                    .message("An error occurred while processing your request. Please try again later.")
                    .build()
            );
        }
    }
}
