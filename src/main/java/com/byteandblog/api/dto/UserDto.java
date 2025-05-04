package com.byteandblog.api.dto;

import com.byteandblog.api.model.User;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String profileImage;
    private boolean isAdmin;

    // Default constructor
    public UserDto() {
    }

    // Constructor with all fields
    public UserDto(Long id, String name, String email, String profileImage, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.isAdmin = isAdmin;
    }

    // Static factory method to create from entity
    public static UserDto fromEntity(User user) {
        return new UserDtoBuilder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .isAdmin("ADMIN".equals(user.getRole())) // Changed to check string value
                .build();
    }

    // Builder pattern method
    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

    // Getters and Setters
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    // Builder class
    public static class UserDtoBuilder {
        private Long id;
        private String name;
        private String email;
        private String profileImage;
        private boolean isAdmin;

        UserDtoBuilder() {
        }

        public UserDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder profileImage(String profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public UserDtoBuilder isAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, name, email, profileImage, isAdmin);
        }
    }
}
