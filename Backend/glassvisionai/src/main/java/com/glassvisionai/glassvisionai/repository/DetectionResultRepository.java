package com.glassvisionai.glassvisionai.repository;


import com.glassvisionai.glassvisionai.entity.DetectionResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DetectionResultRepository extends MongoRepository<DetectionResult, String> {
    DetectionResult findByImageId(String imageId);
}
