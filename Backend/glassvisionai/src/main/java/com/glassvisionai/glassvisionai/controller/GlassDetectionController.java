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
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
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

    @GetMapping(value = "/download/by-filename/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(
            summary = "Download an image by filename",
            description = "Fetches the latest uploaded image from MongoDB using the filename."
    )
    @ApiResponse(responseCode = "200", description = "Image retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Image not found")
    public ResponseEntity<?> downloadImageByFilename(@PathVariable String filename) {
        try {
            // Find the latest file with the given filename
            GridFSFile file = gridFsTemplate.findOne(
                    Query.query(Criteria.where("filename").is(filename))
                            .with(Sort.by(Sort.Direction.DESC, "uploadDate")) // Get the most recent one
            );

            if (file == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found with filename: " + filename);
            }

            GridFsResource resource = gridFsOperations.getResource(file);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getMetadata().getString("_contentType")))
                    .body(new InputStreamResource(resource.getInputStream()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving the image.");
        }
    }


}
