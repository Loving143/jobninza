package com.jobNinza.serviceImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobNinza.exception.BadRequestException;
import com.jobNinza.service.FileStorageService;
@Service
public class FileStorageServiceImpl implements FileStorageService {
	 private final Path uploadDirectory;

	    public FileStorageServiceImpl(@Value("${file.upload-dir}") String uploadDir) {
	    	this.uploadDirectory = Paths.get(uploadDir).toAbsolutePath().normalize();
	    }

	    @Override
	    public String store(MultipartFile file,String storageFileName) {

	        try {

	            // Create directory if it doesn't exist
	            Files.createDirectories(uploadDirectory);

	            Path target =
	                    uploadDirectory.resolve(storageFileName)
	                            .normalize();

	            if (!target.startsWith(uploadDirectory)) {
	                throw new BadRequestException(
	                        "Invalid file path");
	            }
	            Files.copy(file.getInputStream(),target,StandardCopyOption.REPLACE_EXISTING);
	            return target.toString();

	        } catch (IOException ex) {
	        	throw new BadRequestException("Failed to store resume", ex);
	        }
	    }

	    @Override
	    public Resource load(String filePath) {

	        try {

	            Path path = Paths
	                    .get(filePath)
	                    .toAbsolutePath()
	                    .normalize();

	            Resource resource =
	                    new UrlResource(path.toUri());

	            if (!resource.exists() ||
	                    !resource.isReadable()) {

	                throw new BadRequestException(
	                        "Resume file not found");
	            }

	            return resource;

	        } catch (MalformedURLException ex) {

	            throw new BadRequestException(
	                    "Unable to read resume", ex);
	        }
	    }

	    @Override
	    public void delete(String filePath) {

	        try {

	            Files.deleteIfExists(
	                    Paths.get(filePath)
	            );

	        } catch (IOException ex) {

	            throw new BadRequestException(
	                    "Failed to delete resume", ex);
	        }
	    
	}

}
