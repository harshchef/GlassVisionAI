package com.glassvisionai.glassvisionai.entity;

import com.glassvisionai.glassvisionai.entity.BoundingBox;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "detection_results")
public class DetectionResult {

    @Id
    private String id;
    private String imageId;
    @Setter
    private List<BoundingBox> boundingBoxes;

    public DetectionResult(String imageId, List<BoundingBox> boundingBoxes) {
        this.imageId = imageId;
        this.boundingBoxes = boundingBoxes;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getImageId() { return imageId; }
    public List<BoundingBox> getBoundingBoxes() { return boundingBoxes; }

}

