package com.jobNinza.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
public interface FileStorageService {

    String store(MultipartFile file,
            String storageFileName
    );

    Resource load(String filePath);

    void delete(String filePath);
}