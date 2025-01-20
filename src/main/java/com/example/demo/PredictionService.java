//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {
    @Value("${openai.api.key}")
    private String openaiApiKey;
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    @Autowired
    private PredictionRepository predictionRepository;
    @Autowired
    private UserRepository userRepository;

    public PredictionService() {
    }

    public Prediction createPrediction(PredictionRequest predictionRequest) {
        System.out.println("user id  " + predictionRequest.getUserId());
        User user = (User)this.userRepository.findById(predictionRequest.getUserId()).orElseThrow(() -> {
            return new IllegalArgumentException("User not found");
        });
        String predictionResult = this.generatePrediction(predictionRequest.getPredictionData());
        Prediction prediction = new Prediction();
        prediction.setUser(user);
        prediction.setPredictionData(predictionRequest.getPredictionData());
        prediction.setPredictionResult(predictionResult);
        return (Prediction)this.predictionRepository.save(prediction);
    }

    private String generatePrediction(String inputData) {
        String prompt = "Based on the input: " + inputData + ", suggest the best move.";
        String requestBody = String.format("{\n  \"model\": \"gpt-3.5-turbo\",\n  \"messages\": [\n    {\"role\": \"system\", \"content\": \"You are an AI that provides strategic suggestions.\"},\n    {\"role\": \"user\", \"content\": \"%s\"}\n  ]\n}\n", prompt);
        OkHttpClient client = new OkHttpClient();
        Request request = (new Request.Builder()).url("https://api.openai.com/v1/chat/completions").addHeader("Authorization", "Bearer " + this.openaiApiKey).addHeader("Content-Type", "application/json").post(RequestBody.create(requestBody, MediaType.get("application/json"))).build();

        try {
            Response response = client.newCall(request).execute();

            String var7;
            label70: {
                String var10;
                label71: {
                    try {
                        if (!response.isSuccessful()) {
                            System.err.println("Response code: " + response.code());
                            System.err.println("Response message: " + response.message());
                            if (response.body() != null) {
                                System.err.println("Response body: " + response.body().string());
                            }

                            var7 = "Unable to generate prediction. Please try again.";
                            break label70;
                        }

                        if (response.isSuccessful() && response.body() != null) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            String responseBody = response.body().string();
                            JsonNode jsonNode = objectMapper.readTree(responseBody);
                            PrintStream var10000 = System.out;
                            JsonNode var10001 = jsonNode.get("choices").get(0).get("message");
                            var10000.println("ai data " + var10001.get("content").asText());
                            var10 = jsonNode.get("choices").get(0).get("message").get("content").asText();
                            break label71;
                        }

                        var7 = "Unable to generate prediction. Try again.";
                    } catch (Throwable var12) {
                        if (response != null) {
                            try {
                                response.close();
                            } catch (Throwable var11) {
                                var12.addSuppressed(var11);
                            }
                        }

                        throw var12;
                    }

                    if (response != null) {
                        response.close();
                    }

                    return var7;
                }

                if (response != null) {
                    response.close();
                }

                return var10;
            }

            if (response != null) {
                response.close();
            }

            return var7;
        } catch (IOException var13) {
            IOException e = var13;
            e.printStackTrace();
            return "Error connecting to prediction service.";
        }
    }

    public Prediction getPredictionById(Long id) {
        return (Prediction)this.predictionRepository.findById(id).orElse(null);
    }

    public List<Prediction> getAllPredictionsByUserId(Long userId) {
        return this.predictionRepository.findAllByUserId(userId);
    }
}
