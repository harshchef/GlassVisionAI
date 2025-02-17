package com.glassvisionai.glassvisionai.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glassvisionai.glassvisionai.entity.ImagePrediction;
import com.glassvisionai.glassvisionai.model.ImagePredictionResponse;
import com.glassvisionai.glassvisionai.repository.ImagePredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImagePredictionService {

    @Autowired
    private ImagePredictionRepository repository;

    @Autowired
    private ObjectMapper objectMapper; // Jackson for JSON parsing

    public List<ImagePredictionResponse> getPredictionsByUsername(String username) {
        List<ImagePrediction> predictions = repository.findByUsername(username);

        return predictions.stream().map(prediction -> {
            try {
                // Convert `predictions` JSON string to `JsonNode`
                JsonNode predictionJson = objectMapper.readTree(prediction.getPredictions());

                return new ImagePredictionResponse(
                        prediction.getId(),
                        predictionJson
                );

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }
}
