package com.byteandblog.api.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> errors;

    // Default constructor
    public ValidationErrorResponse() {
    }

    // Constructor with all fields
    public ValidationErrorResponse(LocalDateTime timestamp, int status, String error, 
                                  String message, String path, Map<String, String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    // Builder pattern method
    public static ValidationErrorResponseBuilder builder() {
        return new ValidationErrorResponseBuilder();
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    // Builder class
    public static class ValidationErrorResponseBuilder {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private Map<String, String> errors;

        ValidationErrorResponseBuilder() {
        }

        public ValidationErrorResponseBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ValidationErrorResponseBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ValidationErrorResponseBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ValidationErrorResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ValidationErrorResponseBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ValidationErrorResponseBuilder errors(Map<String, String> errors) {
            this.errors = errors;
            return this;
        }

        public ValidationErrorResponse build() {
            return new ValidationErrorResponse(timestamp, status, error, message, path, errors);
        }
    }
}
