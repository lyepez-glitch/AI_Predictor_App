//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        if (this.userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists.");
        } else {
            user.setCreatedAt(LocalDateTime.now());
            this.userRepository.save(user);
        }
    }

    public boolean validateCredentials(String email, String password) {
        User user = this.userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    public boolean resetPassword(String email, String newPassword) {
        User user = this.userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            this.userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public User getUser(Long id) {
        return (User)this.userRepository.findById(id).orElse((Object)null);
    }

    public void saveUser(User user) {
        if (user.getId() != null && this.userRepository.existsById(user.getId())) {
            User existingUser = (User)this.userRepository.findById(user.getId()).orElseThrow(() -> {
                return new IllegalArgumentException("User not found.");
            });
            existingUser.setWeight(user.getWeight());
            existingUser.setHeight(user.getHeight());
            existingUser.setTrainingHabits(user.getTrainingHabits());
            this.userRepository.save(existingUser);
        } else {
            user.setCreatedAt(LocalDateTime.now());
            this.userRepository.save(user);
        }

    }
}
