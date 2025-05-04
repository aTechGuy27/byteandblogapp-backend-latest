package com.byteandblog.api.service;

import com.byteandblog.api.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class FileStorageService {
    
    private static final Logger logger = Logger.getLogger(FileStorageService.class.getName());
    
    @Value("${application.uploads.directory:uploads}")
    private String uploadDir;
    
    @Value("${application.base-url:http://localhost:8080}")
    private String baseUrl;
    
    public String storeFile(MultipartFile file, String subdirectory) {
        try {
            // Create the directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir, subdirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                logger.info("Created directory: " + uploadPath.toAbsolutePath());
            }
            
            // Generate a unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID().toString() + fileExtension;
            
            // Save the file
            Path targetLocation = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Stored file: " + targetLocation.toAbsolutePath());
            
            // Return the URL to access the file
            return baseUrl + "/uploads/" + subdirectory + "/" + filename;
        } catch (IOException ex) {
            logger.severe("Could not store file: " + ex.getMessage());
            throw new BadRequestException("Could not store file. Please try again.");
        }
    }
}
