package com.byteandblog.api.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Configuration
public class FileStorageConfig {
    
    private static final Logger logger = Logger.getLogger(FileStorageConfig.class.getName());
    
    @Value("${application.uploads.directory:uploads}")
    private String uploadsDirectory;
    
    @PostConstruct
    public void init() {
        try {
            Path uploadsPath = Paths.get(uploadsDirectory);
            if (!Files.exists(uploadsPath)) {
                Files.createDirectories(uploadsPath);
                logger.info("Created uploads directory: " + uploadsPath.toAbsolutePath());
            } else {
                logger.info("Uploads directory already exists: " + uploadsPath.toAbsolutePath());
            }
            
            // Create subdirectories
            Path profilePath = Paths.get(uploadsDirectory, "profile");
            if (!Files.exists(profilePath)) {
                Files.createDirectories(profilePath);
                logger.info("Created profile uploads directory: " + profilePath.toAbsolutePath());
            }
        } catch (IOException e) {
            logger.severe("Could not create uploads directory: " + e.getMessage());
            throw new RuntimeException("Could not create uploads directory", e);
        }
    }
}
