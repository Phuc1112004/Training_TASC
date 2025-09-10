package com.example.demojavaspringboot.dto;

// Response DTO: trả dữ liệu về client
public class UserResponseDTO {
    private long id;
    private String username;
    private String email;

    // Getter & Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
