package com.glassvisionai.glassvisionai.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.glassvisionai.glassvisionai.entity.ImagePrediction;
import com.glassvisionai.glassvisionai.entity.User;
import com.glassvisionai.glassvisionai.repository.ImagePredictionRepository;
import com.glassvisionai.glassvisionai.service.UserService;
import com.glassvisionai.glassvisionai.service.WindowDetectionService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/images")
public class WindowDetectionController {

    private final GridFsTemplate gridFsTemplate;
    private final UserService userService;
    private final WindowDetectionService windowDetectionService;
    private final ImagePredictionRepository imagePredictionRepository;

    @Autowired
    public WindowDetectionController(GridFsTemplate gridFsTemplate,
                                     UserService userService,
                                     WindowDetectionService windowDetectionService,
                                     ImagePredictionRepository imagePredictionRepository) {
        this.gridFsTemplate = gridFsTemplate;
        this.userService = userService;
        this.windowDetectionService = windowDetectionService;
        this.imagePredictionRepository = imagePredictionRepository;
    }

    @Operation(summary = "Upload an image and detect windows",
            description = "Uploads an image, stores it, processes it with the AI model, and saves the predictions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded and processed successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid image file"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Server error during processing")
    })
    @PostMapping(value = "/upload/{username}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(
            @PathVariable String username,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty. Please upload a valid file.");
        }

        System.out.println("/upload is triggered");

        // Store image in GridFS
        ObjectId fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());

        // Find user by username and update with image details
        Optional<User> optionalUser = Optional.ofNullable(userService.findByUsername(username));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setImageId(fileId.toString());
            user.setImageName(file.getOriginalFilename());
            userService.updateUser(user);

            // Call WindowDetectionService to get predictions
            JsonNode predictionResponse = windowDetectionService.detectWindows(file);

            // Save prediction results in ImagePrediction collection
            ImagePrediction imagePrediction = new ImagePrediction();
            imagePrediction.setUsername(username);
            imagePrediction.setImageName(file.getOriginalFilename());
            imagePrediction.setPredictions(predictionResponse.toString()); // Store JSON as String

            imagePredictionRepository.save(imagePrediction);

            return ResponseEntity.ok("Image uploaded and processed for predictions for user: " + username);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}

