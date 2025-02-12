package com.glassvisionai.glassvisionai.controller;



import com.glassvisionai.glassvisionai.service.FlaskClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class FlaskClientController {

    private final FlaskClientService flaskClientService;

    public FlaskClientController(FlaskClientService flaskClientService) {
        this.flaskClientService = flaskClientService;
    }

    @Operation(summary = "Detect objects in an uploaded image",
            description = "Uploads an image to the Flask AI model and returns detected bounding boxes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful detection",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid image file"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping(value = "/detect", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> detectObjects(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "File is empty. Please upload a valid image."));
        }

        System.out.println("/detect endpoint triggered");

        try {
            Map<String, Object> response = flaskClientService.sendImageToFlask(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error processing the image", "details", e.getMessage()));
        }
    }
}
