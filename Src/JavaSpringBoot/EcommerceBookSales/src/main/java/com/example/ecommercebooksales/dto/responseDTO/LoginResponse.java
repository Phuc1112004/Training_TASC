package com.example.ecommercebooksales.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String accessToken;
    private String refreshToken;
}

