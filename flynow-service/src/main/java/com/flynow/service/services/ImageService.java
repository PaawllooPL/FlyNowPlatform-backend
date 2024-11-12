package com.flynow.service.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class ImageService {

    @Value("${image.upload.path}")
    private String uploadDirectory;

    /**
     * @param file File to save
     * @return Name of file including its extension
     * @throws IOException
     */
    public String saveImageToStorage(MultipartFile file) throws IOException {
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFilename);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.write(filePath, file.getBytes());

        return uniqueFilename;
    }

    public byte[] getImageFromStorage(String filename) throws IOException {
        Path imagePath = Path.of(uploadDirectory, filename);
        if (Files.exists(imagePath)) {
            return Files.readAllBytes(imagePath);
        } else {
            return null;
        }
    }

    public boolean deleteImageFromStorage(String filename) throws IOException {
        Path imagePath = Path.of(uploadDirectory, filename);
        return Files.deleteIfExists(imagePath);
    }
}
