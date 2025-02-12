package com.glassvisionai.glassvisionai.controller;

import com.glassvisionai.glassvisionai.entity.BoundingBox;
import com.glassvisionai.glassvisionai.entity.DetectionResult;
import com.glassvisionai.glassvisionai.service.DetectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/glass")
@Tag(name = "Glass Detection", description = "APIs for Glass Detection")
public class GlassDetectionController {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations gridFsOperations;

    @Autowired
    private DetectionService detectionService;


    @Operation(
            summary = "Upload an image",
            description = "Uploads an image file to the server and returns its ObjectId from MongoDB."
    )
    @ApiResponse(responseCode = "200", description = "Image uploaded successfully")
    @ApiResponse(responseCode = "400", description = "Invalid file input")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(
            @Parameter(
                    description = "The image file to upload",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary"))
            )
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty. Please upload a valid file.");
        }
        System.out.println("/upload is triggered");

        ObjectId fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
        return ResponseEntity.ok("Image uploaded with ID: " + fileId.toString());
    }

    @PostMapping("/detect/{imageId}")
    public ResponseEntity<String> detectGlass(@PathVariable String imageId) {
        List<BoundingBox> boundingBoxes = Arrays.asList(
                new BoundingBox(50, 60, 100, 120),
                new BoundingBox(200, 150, 90, 100)
        );

        DetectionResult result = new DetectionResult(imageId, boundingBoxes);
        detectionService.saveDetectionResult(result);

        return ResponseEntity.ok("Detection completed for imageId: " + imageId);
    }

    @GetMapping("/results/{imageId}")
    public ResponseEntity<DetectionResult> getDetectionResults(@PathVariable String imageId) {
        Optional<DetectionResult> result = detectionService.getDetectionResult(imageId);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String id) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new org.springframework.data.mongodb.core.query.Query().addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("_id").is(id)));

        if (gridFSFile == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + gridFSFile.getFilename() + "\"")
                .body(new InputStreamResource(gridFsOperations.getResource(gridFSFile).getInputStream()));
    }
}
