package com.byteandblog.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byteandblog.api.model.FileUpload;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

	List<FileUpload> findByUserIdOrderByCreatedAtDesc(Long userId);

	Page<FileUpload> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

	long countByCreatedAtAfter(LocalDateTime date);

	long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
