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

@RestController
@RequestMapping("/api")
@Tag(name = "Object Detection API", description = "API for detecting objects in images using Flask + YOLOv5")
public class FlaskClientController {

    private final FlaskClientService flaskClientService;

    public FlaskClientController(FlaskClientService flaskClientService) {
        this.flaskClientService = flaskClientService;
    }

    @Operation(
            summary = "Detect objects in an image",
            description = "Uploads an image to the Flask backend for object detection and returns bounding box coordinates."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful detection",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid image file"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/detect")
    public ResponseEntity<Map<String, Object>> detectObjects(
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(flaskClientService.sendImageToFlask(file));
    }
}
