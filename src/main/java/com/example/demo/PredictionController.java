//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping({"/submit"})
//    public String showSubmitForm(Model model) {
//        model.addAttribute("predictionRequest", new PredictionRequest());
//
//        return "submit-prediction";
//    }

//@GetMapping("/submit")
//public String showSubmitForm(@RequestHeader(value = "Authorization", required = false) String authHeader,
//                             Model model) {
//    model.addAttribute("predictionRequest", new PredictionRequest());
//
//    if (authHeader != null && authHeader.startsWith("Bearer ")) {
//        String token = authHeader.replace("Bearer ", "");
//        String email = jwtUtil.validateToken(token);
//        if (email != null) {
//            User user = userRepository.findByEmail(email);
//            model.addAttribute("user", user);
//        }
//    }
//
//    return "submit-prediction";
//}

@GetMapping("/submit")
public String showSubmitForm(@RequestParam(required = false) Long userId,@RequestParam(required = false) String token, Model model) {
    System.out.println("userId " + userId);
    System.out.println("token: " + token);
    if (userId != null) {
        User user = userRepository.findById(userId).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("token", token);
    } else {
        model.addAttribute("user", null);  // avoids Thymeleaf errors
    }
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
                System.out.println("prediction "+ prediction.getPredictionResult());
//                return prediction.getId().toString();
                return prediction.getPredictionResult();
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

    @GetMapping("/all/{userId}")
    @ResponseBody
    public List<PredictionDTO> getAllPredictions(@PathVariable Long userId) {
        return predictionService.getAllPredictionsByUserId(userId)
                .stream()
                .map(PredictionDTO::new)
                .toList();
    }


//    @GetMapping({"/all/{userId}"})
//    @ResponseBody
//    public List<Prediction> getAllPredictions(@PathVariable Long userId, Model model) {
//        User user = this.userRepository.findById(userId).orElse(null);
//        return predictionService.getAllPredictionsByUserId(userId);
////        if (user == null) {
////            model.addAttribute("errorMessage", "User not found");
////            return "predictions-list";
////        } else {
////            List<Prediction> predictions = this.predictionService.getAllPredictionsByUserId(user.getId());
////            if (predictions.isEmpty()) {
////                model.addAttribute("infoMessage", "No predictions found for the user");
////            } else {
////                model.addAttribute("predictions", predictions);
////
////            }
////
////            return "predictions-list";
////
////        }
//    }
}
