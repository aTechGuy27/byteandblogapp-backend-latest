package com.byteandblog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {
    
    @NotBlank(message = "Token is required")
    private String token;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Default constructor
    public ResetPasswordRequest() {
    }

    // Constructor with all fields
    public ResetPasswordRequest(String token, String password) {
        this.token = token;
        this.password = password;
    }

    // Builder pattern method
    public static ResetPasswordRequestBuilder builder() {
        return new ResetPasswordRequestBuilder();
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Builder class
    public static class ResetPasswordRequestBuilder {
        private String token;
        private String password;

        ResetPasswordRequestBuilder() {
        }

        public ResetPasswordRequestBuilder token(String token) {
            this.token = token;
            return this;
        }

        public ResetPasswordRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public ResetPasswordRequest build() {
            return new ResetPasswordRequest(token, password);
        }
    }
}
