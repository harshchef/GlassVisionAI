package com.glassvisionai.glassvisionai.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FlaskClientService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String FLASK_API_URL = "http://127.0.0.1:5000/detect"; // Change to your Flask endpoint

    public FlaskClientService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Sends an image to the Flask API for object detection.
     * @param file MultipartFile containing the image.
     * @return Bounding box coordinates.
     */
    public Map<String, Object> sendImageToFlask(MultipartFile file) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Create request entity
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

            // Send request
            ResponseEntity<String> response = restTemplate.exchange(
                    FLASK_API_URL, HttpMethod.POST, requestEntity, String.class);

            // Parse JSON response
            return parseResponse(response.getBody());

        } catch (IOException e) {
            throw new RuntimeException("Error processing image", e);
        }
    }

    /**
     * Parses JSON response from Flask API.
     * @param responseString JSON response as a String.
     * @return Parsed bounding box coordinates.
     */
    private Map<String, Object> parseResponse(String responseString) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseString);
            Map<String, Object> result = new HashMap<>();

            // Extract bounding boxes
            JsonNode boxes = rootNode.path("bounding_boxes");
            result.put("bounding_boxes", boxes);

            return result;

        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }
}
