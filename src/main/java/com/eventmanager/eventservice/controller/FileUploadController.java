package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.service.api.StorageAPIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(path = "/api/v1/storage")
@Controller
public class FileUploadController {

    private final StorageAPIService storageService;


    public FileUploadController(StorageAPIService storageService) {
        this.storageService = storageService;
    }
//
//    @PostMapping("/upload")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//
//        String outputPath = storageService.uploadFile(file);
//
//        return new ResponseEntity<>(outputPath,
//                HttpStatus.CREATED);
//    }

    @GetMapping(path = {"/image/{directory}/{subdirectory}/{filename}", "/image/{directory}/{filename}"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getImage(@PathVariable Map<String, String> pathVarsMap) {
        String directory = pathVarsMap.get("directory");
        String subdirectory = pathVarsMap.get("subdirectory");
        if (subdirectory != null) {
            directory += "/" + subdirectory;
        }


        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
                storageService.getFile(directory, pathVarsMap.get("filename")));
    }
}