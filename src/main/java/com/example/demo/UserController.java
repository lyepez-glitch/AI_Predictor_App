//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/users"})
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public UserController() {
    }

    @GetMapping({"/login"})
    public String loginPage() {
        return "login";
    }

//    @PostMapping({"/userDetails/{userId}"})
//    public String submitUserDetails(@PathVariable Long userId, @RequestParam double weight, @RequestParam double height, @RequestParam String trainingHabits, Model model) {
//        User user = this.userService.getUser(userId);
//        if (user != null) {
//            user.setWeight(weight);
//            user.setHeight(height);
//            user.setTrainingHabits(trainingHabits);
//            this.userService.saveUser(user);
//        }
//
//        model.addAttribute("user", user);
//        return "view-user";
//    }

    @PostMapping("/userDetails/{userId}")
    @ResponseBody
    public ResponseEntity<?> submitUserDetails(
            @PathVariable Long userId,
            @RequestParam double weight,
            @RequestParam double height,
            @RequestParam String trainingHabits) {

        User user = userService.getUser(userId);
        if (user != null) {
            user.setWeight(weight);
            user.setHeight(height);
            user.setTrainingHabits(trainingHabits);
            userService.saveUser(user);
            return ResponseEntity.ok(new UserDTO(user));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping({"/login"})
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> credentials) {
        String email = (String)credentials.get("email");
        String password = (String)credentials.get("password");
        if (this.userService.validateCredentials(email, password)) {
            String token = this.jwtUtil.generateToken(email);
            User user = this.userRepository.findByEmail(email);
            return ResponseEntity.ok(Map.of("token", token, "userId", user.getId().toString(), "message", "Login successful."));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password."));
        }
    }

    @GetMapping({"/register"})
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping({"/register"})
    public String registerUser(@ModelAttribute User user) {
        try {
            this.userService.registerUser(user);
            return "redirect:/users/login";
        } catch (IllegalArgumentException var3) {
            return "register";
        }
    }

    @GetMapping({"/reset-password"})
    public String resetPasswordPage() {
        return "reset-password";
    }

    @PostMapping({"/reset-password"})
    public String resetPassword(@RequestParam String email, @RequestParam String newPassword, Model model) {
        if (this.userService.resetPassword(email, newPassword)) {
            User user = userService.getUserByEmail(email);
            model.addAttribute("message", "Password reset successfully.");
//            return "redirect:/predict/submit";
            if(user != null && user.getId() != null){
                System.out.println("Type: " + user.getId().getClass().getSimpleName());
                String token = this.jwtUtil.generateToken(email);
                return "redirect:/predict/submit?userId=" + user.getId() + "&token=" + token;
            }else{
                return "redirect:/predict/submit";
            }

        } else {
            return "Email not found.";
        }
    }



//    @GetMapping({"/{id}"})
//    @ResponseBody
//    public User getUser(@PathVariable Long id, Model model) {
//        return userService.getUser(id);
////        User user = this.userService.getUser(id);
////        if (user != null) {
////            model.addAttribute("user", user);
////            return "view-user";
////        } else {
////            model.addAttribute("message", "User not found.");
////            return "view-user";
////        }
//    }

//    @GetMapping("/{id}")
//    @ResponseBody
//    public UserDTO getUser(@PathVariable Long id) {
//        User user = userService.getUser(id);
//        return new UserDTO(user);
//    }


//    @GetMapping("/users/{id}")
//    public UserDTO getUser(@PathVariable Long id) {
//        User user = userService.findById(id);
//        return new UserDTO(user); // map manually or use ModelMapper
//    }

    @GetMapping("/{id}")
    @ResponseBody
    public UserDTO getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return new UserDTO(user);
    }

}
