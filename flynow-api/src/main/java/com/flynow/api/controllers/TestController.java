package com.flynow.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @PostMapping()
    public ResponseEntity<String> testPost() {
        return ResponseEntity.ok().body("Post Hello");
    }
    @GetMapping("/create-data")
    public ResponseEntity<String> testGetCreateData() {

        return ResponseEntity.ok().body("Get Hello create-data");
    }
    @GetMapping("/auth-test")
    public ResponseEntity<String> authTestGet() {

        return ResponseEntity.ok().body("Auth test worked");
    }
}
