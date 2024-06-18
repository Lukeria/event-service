package com.eventmanager.eventservice.service.api;

import org.springframework.web.multipart.MultipartFile;

public interface StorageAPIService {
//    String uploadFile(MultipartFile file);

    byte[] getFile(String fileGroup, String filename);
}
