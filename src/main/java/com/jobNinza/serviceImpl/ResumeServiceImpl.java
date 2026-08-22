package com.jobNinza.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jobNinza.entity.Resume;
import com.jobNinza.entity.Users;
import com.jobNinza.enums.ResumeStatus;
import com.jobNinza.exception.BadRequestException;
import com.jobNinza.repository.ResumeRepository;
import com.jobNinza.repository.UserRepository;
import com.jobNinza.service.FileStorageService;
import com.jobNinza.service.ResumeService;
import com.jobNinza.util.SnowflakeIdGenerator;
import org.springframework.core.io.Resource;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService{

	@Autowired
    private FileStorageService fileStorageService;
	@Autowired
    private ResumeRepository resumeRepository;
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private SnowflakeIdGenerator idGenerator;

	public Resume uploadResume(MultipartFile file, long userId) {

        // 1. Validate uploaded file
        validateFile(file);

        // 2. Find the user
        Users user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        // 3. Generate unique Resume ID
        Long resumeId = idGenerator.nextId();

        // 4. Extract original file name
        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null ||
                originalFileName.isBlank()) {

            throw new BadRequestException(
                    "Invalid file name");
        }

        originalFileName =
                StringUtils.cleanPath(originalFileName);

        // 5. Determine file extension
        String extension =
                getFileExtension(originalFileName);

        // 6. Generate server-side storage filename
        String storageFileName =
                resumeId + extension;

        String filePath = null;

        try {

            // 7. Store physical file
            filePath = fileStorageService.store(
                    file,
                    storageFileName
            );

            // 8. Create Resume entity
            Resume resume = new Resume();

            resume.setId(resumeId);
            resume.setUser(user);
            resume.setOriginalFileName(originalFileName);
            resume.setStorageFileName(storageFileName);
            resume.setFilePath(filePath);
            resume.setContentType(file.getContentType());
            resume.setFileSize(file.getSize());
            resume.setStatus(ResumeStatus.UPLOADED);

            LocalDateTime now = LocalDateTime.now();

            resume.setCreatedAt(now);
            resume.setUpdatedAt(now);

            // 9. Save metadata
            return resumeRepository.save(resume);

        } catch (Exception ex) {

            /*
             * File was successfully stored but DB
             * operation failed.
             *
             * Delete the file to avoid an orphan file.
             */
            if (filePath != null) {
                fileStorageService.delete(filePath);
            }

            throw new BadRequestException(
                    "Failed to upload resume", ex);
        }
    }

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException(
                    "Resume file cannot be empty");
        }

        // 5 MB limit
        long maxFileSize = 5 * 1024 * 1024;

        if (file.getSize() > maxFileSize) {
            throw new BadRequestException(
                    "Resume file size cannot exceed 5 MB");
        }

        String contentType = file.getContentType();

        if (!isValidFileType(contentType)) {
            throw new BadRequestException(
                    "Only PDF and DOCX files are allowed");
        }
    }

    private boolean isValidFileType(String contentType) {

//        return MediaType.APPLICATION_PDF_VALUE.equals(contentType)
//                ||
//                "application/vnd.openxmlformats-officedocument" +
//                ".wordprocessingml.document".equals(contentType);
    	return true;
    }

    private String getFileExtension(String fileName) {

        int index = fileName.lastIndexOf('.');

        if (index == -1) {
            throw new BadRequestException(
                    "File extension is missing");
        }

        return fileName.substring(index).toLowerCase();
    }

}