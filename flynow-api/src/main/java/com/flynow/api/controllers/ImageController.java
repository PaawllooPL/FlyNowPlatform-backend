package com.flynow.api.controllers;

import com.flynow.service.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

import static com.flynow.api.ApiPathSegments.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH + IMAGE)
public class ImageController {

    private final ImageService imageService;

//    @GetMapping(IMAGE_FILENAME_PARAMETER)
    @GetMapping(value = IMAGE_FILENAME_PARAMETER, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Resource imageResource = imageService.getImageFromStorage(filename);
            return ResponseEntity.ok(imageResource);
        }
        catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }
}
