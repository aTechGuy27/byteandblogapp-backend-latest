package com.byteandblog.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    // Default constructor
    public ForgotPasswordRequest() {
    }

    // Constructor with all fields
    public ForgotPasswordRequest(String email) {
        this.email = email;
    }

    // Builder pattern method
    public static ForgotPasswordRequestBuilder builder() {
        return new ForgotPasswordRequestBuilder();
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Builder class
    public static class ForgotPasswordRequestBuilder {
        private String email;

        ForgotPasswordRequestBuilder() {
        }

        public ForgotPasswordRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ForgotPasswordRequest build() {
            return new ForgotPasswordRequest(email);
        }
    }
}
