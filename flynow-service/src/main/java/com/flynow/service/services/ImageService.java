package com.flynow.service.services;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
public class ImageService {

    @Value("${image.upload.path}")
    private String uploadDirectory;
    private final Logger logger = LoggerFactory.getLogger(ImageService.class);
    /**
     * @param originalFileName original file name
     * @param bytes image data in byte array
     * @return Name of file including its extension
     * @throws IOException
     */
    public Optional<String> saveImageToStorage(String originalFileName, byte[] bytes) {
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFileName;

        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFilename);

        try {
            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.write(filePath, bytes);
        } catch (IOException e) {
            logger.error("Error saving image to storage: ", e);
            return Optional.empty();
        }

        return Optional.of(uniqueFilename);
    }

    public Resource getImageFromStorage(String filename) throws IOException {
        Path imagePath = Path.of(uploadDirectory, filename);
        if (Files.exists(imagePath)) {
            return new FileSystemResource(imagePath);
        } else {
            throw new FileNotFoundException(filename);
        }
    }

    public boolean deleteImageFromStorage(String filename) throws IOException {
        Path imagePath = Path.of(uploadDirectory, filename);
        return Files.deleteIfExists(imagePath);
    }
}
