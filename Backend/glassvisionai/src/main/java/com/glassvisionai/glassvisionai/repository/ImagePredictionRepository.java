package com.glassvisionai.glassvisionai.repository;

import com.glassvisionai.glassvisionai.entity.ImagePrediction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagePredictionRepository extends MongoRepository<ImagePrediction, String> {
}
