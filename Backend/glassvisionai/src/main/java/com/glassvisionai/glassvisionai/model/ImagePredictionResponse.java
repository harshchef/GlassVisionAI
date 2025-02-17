package com.glassvisionai.glassvisionai.model;
import com.fasterxml.jackson.databind.JsonNode;

public class ImagePredictionResponse {
    private String imageName;
    private JsonNode predictions; // JSON object

    public ImagePredictionResponse(String imageName, JsonNode predictions) {
        this.imageName = imageName;
        this.predictions = predictions;
    }

    public String getImageName() {
        return imageName;
    }

    public JsonNode getPredictions() {
        return predictions;
    }
}
