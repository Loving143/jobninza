package com.jobNinza.serviceImpl;

import java.time.LocalDateTime;

import com.jobNinza.service.ResumeAIParserService;
import com.jobNinza.util.ResumeData;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.jobNinza.service.ResumeExtractorService;
import com.jobNinza.service.ResumeService;
import com.jobNinza.util.SnowflakeIdGenerator;

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
	@Autowired
	private ResumeExtractorService resumeExtractorService;
    @Autowired
    private ResumeAIParserService resumeAIParserService;

	@Override

	public ResumeData uploadResume(MultipartFile file,String email) {
        validateFile(file);
        Users user = userRepository.findByEmail(email).orElseThrow(() ->
                        new BadRequestException("User not found"));
        Long resumeId = idGenerator.nextId();
        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
        	throw new BadRequestException("Invalid file name");
        }
        originalFileName = StringUtils.cleanPath(originalFileName);
        String extension = getFileExtension(originalFileName);
        String storageFileName = resumeId + extension;

        String filePath = null;

        try {
            filePath = fileStorageService.store(file,storageFileName);

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

            String text = resumeExtractorService.extractText(file);
            ResumeData data = resumeAIParserService.parseResume(text);
            System.out.println("Extracted text: " + text);
            // 9. Save metadata
            resumeRepository.save(resume);
            return data;

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
            throw new BadRequestException("Resume file cannot be empty");
        }

        // 10 MB limit
        long maxFileSize = 10 * 1024 * 1024;

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