package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.service.api.StorageAPIService;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

public interface StorageService extends StorageAPIService {

    String storeFile(MultipartFile file, String directoryName, String subdirectoryName);

    String getImageSrcUrl(String imageUrl);

    void deleteFile(String filename, String directoryName, String subdirectoryName);

    void deleteSubDirectory(String directoryName, String subdirectoryName);

    default String parseFileName(String filepath) {
        if(filepath == null){
            return null;
        }

        int index = filepath.lastIndexOf("/");
        if (index != -1) {
            return filepath.substring(index + 1);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while parsing filename");
    }
}
