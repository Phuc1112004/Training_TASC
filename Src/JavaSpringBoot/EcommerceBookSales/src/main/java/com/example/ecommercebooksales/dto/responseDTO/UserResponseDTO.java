package com.example.ecommercebooksales.dto.responseDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private Long userId;
    private String userName;
    private String email;
    private String phone;
    private String role;
    private LocalDateTime createAt;
}
