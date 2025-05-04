package com.byteandblog.api.dto;

import com.byteandblog.api.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class UserProfileDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String address;
    private String profileImage;
    private String role;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastLoginAt;

    // Default constructor
    public UserProfileDto() {
    }

    // All-args constructor
    public UserProfileDto(Long id, String name, String email, Integer age, String address, 
                         String profileImage, String role, LocalDateTime createdAt, 
                         LocalDateTime lastLoginAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
        this.profileImage = profileImage;
        this.role = role;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    // Static factory method to create from User entity
    public static UserProfileDto fromEntity(User user) {
        return new UserProfileDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getAge(),
            user.getAddress(),
            user.getProfileImage(),
            user.getRole(),
            user.getCreatedAt(),
            user.getLastLoginAt()
        );
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    // Builder class
    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private Integer age;
        private String address;
        private String profileImage;
        private String role;
        private LocalDateTime createdAt;
        private LocalDateTime lastLoginAt;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder profileImage(String profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder lastLoginAt(LocalDateTime lastLoginAt) {
            this.lastLoginAt = lastLoginAt;
            return this;
        }

        public UserProfileDto build() {
            return new UserProfileDto(id, name, email, age, address, profileImage, role, createdAt, lastLoginAt);
        }
    }
}