package com.glassvisionai.glassvisionai.service;

import com.glassvisionai.glassvisionai.entity.DetectionResult;
import com.glassvisionai.glassvisionai.repository.DetectionResultRepository;
import com.glassvisionai.glassvisionai.entity.DetectionResult;
import com.glassvisionai.glassvisionai.repository.DetectionResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetectionService {

    @Autowired
    private DetectionResultRepository detectionResultRepository;

    public void saveDetectionResult(DetectionResult result) {
        detectionResultRepository.save(result);
    }

    public Optional<DetectionResult> getDetectionResult(String imageId) {
        return Optional.ofNullable(detectionResultRepository.findByImageId(imageId));
    }
}
