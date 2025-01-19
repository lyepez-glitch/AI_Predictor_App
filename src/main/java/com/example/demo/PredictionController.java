//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/predict"})
public class PredictionController {
    @Autowired
    private PredictionService predictionService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    public PredictionController() {
    }

    @GetMapping({"/submit"})
    public String showSubmitForm(Model model) {
        model.addAttribute("predictionRequest", new PredictionRequest());
        return "submit-prediction";
    }

    @PostMapping({"/submit"})
    @ResponseBody
    public String submitPrediction(@RequestHeader("Authorization") String authHeader, @ModelAttribute PredictionRequest predictionRequest, Model model) {
        System.out.println("auth header " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            String userEmail = this.jwtUtil.validateToken(token);
            if (userEmail == null) {
                return "Unauthorized: Invalid token";
            } else {
                User user = this.userRepository.findByEmail(userEmail);
                predictionRequest.setUserId(user.getId());
                Prediction prediction = this.predictionService.createPrediction(predictionRequest);
                model.addAttribute("prediction", prediction);
                return prediction.getId().toString();
            }
        } else {
            return "Unauthorized: Token missing or malformed";
        }
    }

    @GetMapping({"/{id}"})
    public String getPrediction(@PathVariable Long id, Model model) {
        Prediction prediction = this.predictionService.getPredictionById(id);
        if (prediction != null) {
            model.addAttribute("prediction", prediction);
            return "view-prediction";
        } else {
            model.addAttribute("errorMessage", "Prediction not found.");
            return "view-prediction";
        }
    }

    @GetMapping({"/all/{userId}"})
    public String getAllPredictions(@PathVariable Long userId, Model model) {
        User user = (User)this.userRepository.findById(userId).orElse((Object)null);
        if (user == null) {
            model.addAttribute("errorMessage", "User not found");
            return "predictions-list";
        } else {
            List<Prediction> predictions = this.predictionService.getAllPredictionsByUserId(user.getId());
            if (predictions.isEmpty()) {
                model.addAttribute("infoMessage", "No predictions found for the user");
            } else {
                model.addAttribute("predictions", predictions);
            }

            return "predictions-list";
        }
    }
}
