package com.tourman.app.controllers;

import com.tourman.app.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/image")
public class CloudinaryController {
    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/search/{tag}")
    public ResponseEntity<List<String>> searchByTag(@PathVariable String tag) throws Exception {
        List<String> result = cloudinaryService.searchImageUrlsByTag(tag);
        return ResponseEntity.ok(result);
    }
}
