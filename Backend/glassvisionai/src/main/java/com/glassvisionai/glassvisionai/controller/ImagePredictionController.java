package com.glassvisionai.glassvisionai.controller;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.glassvisionai.glassvisionai.entity.ImagePrediction;
import com.glassvisionai.glassvisionai.model.ImagePredictionResponse;
import com.glassvisionai.glassvisionai.repository.ImagePredictionRepository;
import com.glassvisionai.glassvisionai.service.ImagePredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/image-predictions")
public class ImagePredictionController {

    private final ImagePredictionService imagePredictionService;
    private final ImagePredictionRepository imagePredictionRepository;
    private final ObjectMapper objectMapper;

    public ImagePredictionController(ImagePredictionService imagePredictionService,
                                     ImagePredictionRepository imagePredictionRepository,
                                     ObjectMapper objectMapper) {
        this.imagePredictionService = imagePredictionService;
        this.imagePredictionRepository = imagePredictionRepository;
        this.objectMapper = objectMapper;
    }

    @Operation(summary = "Fetch image predictions by username",
            description = "Retrieves the list of image predictions associated with the specified username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Predictions retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ImagePredictionResponse.class))),
            @ApiResponse(responseCode = "404", description = "No predictions found for the given username"),
            @ApiResponse(responseCode = "500", description = "Server error during retrieval")
    })
    @GetMapping("/{username}")
    public ResponseEntity<List<ImagePredictionResponse>> getPredictions(@PathVariable String username) {
        List<ImagePrediction> predictions = imagePredictionRepository.findByUsername(username);

        if (predictions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ImagePredictionResponse> responseList = predictions.stream().map(prediction -> {
            try {
                JsonNode jsonPredictions = objectMapper.readTree(prediction.getPredictions());
                return new ImagePredictionResponse(prediction.getImageName(), jsonPredictions);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }
}
