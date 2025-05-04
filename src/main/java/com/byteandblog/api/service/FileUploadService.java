package com.byteandblog.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.byteandblog.api.dto.FileUploadDto;
import com.byteandblog.api.exception.BadRequestException;
import com.byteandblog.api.model.FileUpload;
import com.byteandblog.api.model.User;
import com.byteandblog.api.repository.FileUploadRepository;

@Service
public class FileUploadService {

	private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);

	private final FileUploadRepository fileUploadRepository;
	private final UserService userService;

	public FileUploadService(FileUploadRepository fileUploadRepository, UserService userService) {
		this.fileUploadRepository = fileUploadRepository;
		this.userService = userService;
	}

	@Value("${application.uploads.directory}")
	private String uploadDirectory;

	@Value("${application.uploads.base-url}")
	private String baseUrl;

	 @Transactional
	    public FileUploadDto uploadFile(MultipartFile file, String userEmail) {
	        log.info("Uploading file: {}", file.getOriginalFilename());
	        
	        if (file.isEmpty()) {
	            log.warn("File is empty");
	            throw new BadRequestException("File is empty");
	        }

	        try {
	            // Create upload directory if it doesn't exist
	            Path uploadPath = Paths.get(uploadDirectory);
	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	                log.debug("Created upload directory: {}", uploadPath);
	            }

	            // Generate a unique filename
	            String originalFilename = file.getOriginalFilename();
	            String extension = FilenameUtils.getExtension(originalFilename);
	            String storedFilename = UUID.randomUUID().toString() + "." + extension;
	            
	            // Save the file
	            Path filePath = uploadPath.resolve(storedFilename);
	            Files.copy(file.getInputStream(), filePath);
	            log.debug("File saved to: {}", filePath);

	            // Create file upload record
	            User user = userService.getUserEntityByEmail(userEmail);
	            
	            FileUpload fileUpload = FileUpload.builder()
	                    .originalName(originalFilename)
	                    .storedName(storedFilename)
	                    .filePath(filePath.toString())
	                    .fileType(file.getContentType())
	                    .fileSize(file.getSize())
	                    .url(baseUrl + "/uploads/" + storedFilename)
	                    .user(user)
	                    .build();
	            
	            fileUpload = fileUploadRepository.save(fileUpload);
	            log.info("File upload record created with id: {}", fileUpload.getId());
	            
	            return FileUploadDto.fromEntity(fileUpload);
	        } catch (IOException e) {
	            log.error("Failed to upload file", e);
	            throw new BadRequestException("Failed to upload file: " + e.getMessage());
	        }
	    }

	    public List<FileUploadDto> getUserUploads(String userEmail) {
	        log.debug("Getting uploads for user: {}", userEmail);
	        
	        User user = userService.getUserEntityByEmail(userEmail);
	        List<FileUpload> uploads = fileUploadRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
	        
	        return uploads.stream()
	                .map(FileUploadDto::fromEntity)
	                .collect(Collectors.toList());
	    }

	    public Page<FileUploadDto> getUserUploadsPageable(String userEmail, int page, int size) {
	        log.debug("Getting pageable uploads for user: {}, page: {}, size: {}", userEmail, page, size);
	        
	        User user = userService.getUserEntityByEmail(userEmail);
	        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
	        Page<FileUpload> uploads = fileUploadRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), pageable);
	        
	        return uploads.map(FileUploadDto::fromEntity);
	    }
}
