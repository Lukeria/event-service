package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.resources.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocationPath;

    @Autowired
    public FileSystemStorageService(ApplicationProperties properties) {

        if (properties.getLocation().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File upload location can not be Empty.");
        }

        this.rootLocationPath = Paths.get(properties.getLocation());
    }

    @Override
    public byte[] getFile(String fileGroup, String filename) {
        Path directory = rootLocationPath.resolve(fileGroup);

        try (Stream<Path> walkFiles = Files.walk(directory)) {
            Optional<Path> filePathOptional = walkFiles
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals(filename))
                    .findFirst();
            return Files.readAllBytes(filePathOptional
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found")));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public String storeFile(MultipartFile file, String directoryName, String subdirectoryName) {
        String filename = "";
        try {
            if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to store empty file.");
            }
            // Create the directory if it doesn't exist
            if (!Files.exists(rootLocationPath)) {
                Files.createDirectories(rootLocationPath);
            }

            Path groupDirectory = rootLocationPath.resolve(directoryName);
            if (!Files.exists(groupDirectory)) {
                Files.createDirectories(groupDirectory);
            }

            Path subDirectory = groupDirectory.resolve(subdirectoryName);
            if (!Files.exists(subDirectory)) {
                Files.createDirectories(subDirectory);
            }

            filename = UuidUtil.generateUuid(16);
            Path outputPath = subDirectory.resolve(filename);

            Files.write(outputPath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }

        return "/api/v1/storage/image/" + directoryName + "/" + subdirectoryName + "/" + filename;
    }

    @Override
    public String getImageSrcUrl(String imageUrl) {
        if (imageUrl == null) {
            return null;
        }

        return ServletUriComponentsBuilder.fromCurrentContextPath().path(imageUrl).toUriString();
    }

    @Override
    public void deleteFile(String filename, String directoryName, String subdirectoryName) {
        String fileGroup = directoryName;
        if (StringUtils.hasLength(subdirectoryName)) {
            fileGroup += "/" + subdirectoryName;
        }

        Path directory = rootLocationPath.resolve(fileGroup);

        try (Stream<Path> walkFiles = Files.walk(directory)) {
            Optional<Path> filePathOptional = walkFiles
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals(filename))
                    .findFirst();


            Files.delete(filePathOptional
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found")));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void deleteSubDirectory(String directoryName, String subdirectoryName) {
        Path directory = rootLocationPath.resolve(directoryName);
        String subdirectoryGroup = directoryName;
        if (StringUtils.hasLength(subdirectoryName)) {
            subdirectoryGroup += "/" + subdirectoryName;
        }
        Path subdirectory = rootLocationPath.resolve(subdirectoryGroup);

        if (!Files.exists(subdirectory)) {
            return;
        }

        try (Stream<Path> walkFiles = Files.walk(subdirectory);
             Stream<Path> walkDirectory = Files.walk(directory)) {
            walkFiles
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);

            Optional<Path> subdirectoryOptional = walkDirectory
                    .filter(Files::isDirectory)
                    .filter(path -> path.getFileName().toString().equals(subdirectoryName))
                    .findFirst();

            if (subdirectoryOptional.isPresent()) {
                Files.delete(subdirectoryOptional
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Directory not found")));
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
