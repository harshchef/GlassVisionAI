package com.glassvisionai.glassvisionai.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.glassvisionai.glassvisionai.service.WindowDetectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/detect")
public class WindowDetectionController {

    private final WindowDetectionService windowDetectionService;

    public WindowDetectionController(WindowDetectionService windowDetectionService) {
        this.windowDetectionService = windowDetectionService;
    }

    @Operation(summary = "Detect windows in an uploaded image",
            description = "Uploads an image to the AI model and returns detected bounding boxes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful detection",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid image file"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping(value = "/windows", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> detectWindows(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "File is empty. Please upload a valid image."));
        }

        System.out.println("/api/detect/windows endpoint triggered");

        try {
            JsonNode response = windowDetectionService.detectWindows(file);
            return ResponseEntity.ok(Map.of("predictions", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error processing the image", "details", e.getMessage()));
        }
    }
}
