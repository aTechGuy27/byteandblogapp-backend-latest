package com.byteandblog.api.dto;

/**
 * Response DTO for contact form submissions
 */
public class ContactResponseDto {

    private boolean success;
    private String message;

    // Default constructor
    public ContactResponseDto() {
    }

    // All-args constructor
    public ContactResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Builder pattern implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean success;
        private String message;

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ContactResponseDto build() {
            return new ContactResponseDto(success, message);
        }
    }

    @Override
    public String toString() {
        return "ContactResponseDto{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
