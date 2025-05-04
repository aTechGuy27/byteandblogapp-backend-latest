package com.byteandblog.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class UserProfileUpdateRequest {
    
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @Min(value = 13, message = "Age must be at least 13")
    @Max(value = 120, message = "Age must be less than 120")
    private Integer age;
    
    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    // Default constructor
    public UserProfileUpdateRequest() {
    }

    // All-args constructor
    public UserProfileUpdateRequest(String name, Integer age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    // Builder class
    public static class Builder {
        private String name;
        private Integer age;
        private String address;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
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

        public UserProfileUpdateRequest build() {
            return new UserProfileUpdateRequest(name, age, address);
        }
    }
}
