package com.glassvisionai.glassvisionai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/glass")
@Tag(name = "Glass Detection", description = "APIs for Glass Detection")
public class GlassDetectionController {

    @GetMapping("/detect")
    @Operation(summary = "Detect Glass in Image", description = "Processes an image and detects glass.")
    public String detectGlass() {
        return "Glass detected!";
    }
}
