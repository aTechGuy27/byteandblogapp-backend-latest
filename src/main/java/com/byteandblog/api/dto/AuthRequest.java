package com.byteandblog.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Default constructor
    public AuthRequest() {
    }

    // Constructor with all fields
    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Builder pattern method
    public static AuthRequestBuilder builder() {
        return new AuthRequestBuilder();
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Builder class
    public static class AuthRequestBuilder {
        private String email;
        private String password;

        AuthRequestBuilder() {
        }

        public AuthRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AuthRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public AuthRequest build() {
            return new AuthRequest(email, password);
        }
    }
}
