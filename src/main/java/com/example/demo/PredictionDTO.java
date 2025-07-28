package com.example.demo;

public class PredictionDTO {
    private Long id;
    private String predictionData;
    public String predictionResult;

    public PredictionDTO(Prediction prediction) {
        this.id = prediction.getId();
        this.predictionData = prediction.getPredictionData();
        this.predictionResult = prediction.getPredictionResult();
    }

    // 👇 Required for JSON serialization
    public Long getId() {
        return id;
    }

    public String getPredictionData() {
        return predictionData;
    }
    public String getPredictionResult() {
        return predictionResult;
    }

}
