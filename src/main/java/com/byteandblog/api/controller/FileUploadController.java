package com.byteandblog.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.byteandblog.api.dto.FileUploadDto;
import com.byteandblog.api.service.FileUploadService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/uploads")
public class FileUploadController {

	private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);
	private final FileUploadService fileUploadService;
	
	private final PagedResourcesAssembler<FileUploadDto> pagedResourcesAssembler;

	

	public FileUploadController(FileUploadService fileUploadService,
			PagedResourcesAssembler<FileUploadDto> pagedResourcesAssembler) {
		this.fileUploadService = fileUploadService;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EntityModel<FileUploadDto>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Uploading file: {}", file.getOriginalFilename());
        
        FileUploadDto uploadedFile = fileUploadService.uploadFile(file, userDetails.getUsername());
        
        EntityModel<FileUploadDto> entityModel = EntityModel.of(uploadedFile,
                linkTo(methodOn(FileUploadController.class).getUserUploadsPageable(0, 10, userDetails)).withRel("user-uploads"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @GetMapping
    public ResponseEntity<List<FileUploadDto>> getUserUploads(
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Getting uploads for user: {}", userDetails.getUsername());
        return ResponseEntity.ok(fileUploadService.getUserUploads(userDetails.getUsername()));
    }

    @GetMapping("/pageable")
    public ResponseEntity<PagedModel<EntityModel<FileUploadDto>>> getUserUploadsPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Getting pageable uploads for user: {}, page: {}, size: {}", userDetails.getUsername(), page, size);
        
        Page<FileUploadDto> uploadsPage = fileUploadService.getUserUploadsPageable(userDetails.getUsername(), page, size);
        
        PagedModel<EntityModel<FileUploadDto>> pagedModel = pagedResourcesAssembler.toModel(
                uploadsPage,
                dto -> EntityModel.of(dto)
        );
        
        return ResponseEntity.ok(pagedModel);
    }
}
