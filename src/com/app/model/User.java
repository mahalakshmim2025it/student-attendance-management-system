package com.app.model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String role; // 'STUDENT', 'INSTRUCTOR', 'ADMIN'

    // Constructor
    public User(int userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters (Encapsulation)
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
}