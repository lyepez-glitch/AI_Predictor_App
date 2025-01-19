//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "USERS"
)
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            name = "WEIGHT"
    )
    private Double weight;
    @Column(
            name = "HEIGHT"
    )
    private Double height;
    @Column(
            name = "TRAINING_HABITS",
            length = 500
    )
    private String trainingHabits;
    @Column(
            name = "USERNAME",
            nullable = false,
            length = 255
    )
    private String username;
    @Column(
            name = "EMAIL",
            nullable = false,
            unique = true,
            length = 255
    )
    private String email;
    @Column(
            name = "PASSWORD",
            nullable = false,
            length = 255
    )
    private String password;
    @Column(
            name = "CREATED_AT",
            nullable = false
    )
    private LocalDateTime createdAt;
    @OneToMany(
            mappedBy = "user"
    )
    private List<Prediction> predictions;

    public User() {
    }

    public List<Prediction> getPredictions() {
        return this.predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return this.height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getTrainingHabits() {
        return this.trainingHabits;
    }

    public void setTrainingHabits(String trainingHabits) {
        this.trainingHabits = trainingHabits;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toString() {
        Long var10000 = this.id;
        return "User{id=" + var10000 + ", username='" + this.username + "', email='" + this.email + "', createdAt=" + String.valueOf(this.createdAt) + "}";
    }
}
