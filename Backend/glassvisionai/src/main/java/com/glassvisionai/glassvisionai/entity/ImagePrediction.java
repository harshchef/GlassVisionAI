package com.glassvisionai.glassvisionai.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "image_predictions")
public class ImagePrediction {

    @Id
    private String id;

    private String username;
    private String imageName;
    private String predictions;  // Stores the AI response as a JSON string

    // Default Constructor
    public ImagePrediction() {
    }

    // Parameterized Constructor
    public ImagePrediction(String username, String imageName, String predictions) {
        this.username = username;
        this.imageName = imageName;
        this.predictions = predictions;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPredictions() {
        return predictions;
    }

    public void setPredictions(String predictions) {
        this.predictions = predictions;
    }

    @Override
    public String toString() {
        return "ImagePrediction{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", imageName='" + imageName + '\'' +
                ", predictions='" + predictions + '\'' +
                '}';
    }
}
