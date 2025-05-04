package com.byteandblog.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    // Default constructor
    public ResourceNotFoundException() {
        super();
    }
    
    // Constructor with message
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    // Constructor with message and cause
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Constructor with cause
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
