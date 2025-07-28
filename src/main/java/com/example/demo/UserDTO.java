package com.example.demo;


public class UserDTO {
    private Long id;
    private String email;
    private double height;
    private double weight;
    private String trainingHabits;

    public UserDTO() {}

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.height = user.getHeight() != null ? user.getHeight() : 0.0;
        this.weight = user.getWeight() != null ? user.getWeight() : 0.0;
        this.trainingHabits = user.getTrainingHabits() != null? user.getTrainingHabits() : "N/A";
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public String getTrainingHabits() { return trainingHabits; }
    // Setters if needed
}
