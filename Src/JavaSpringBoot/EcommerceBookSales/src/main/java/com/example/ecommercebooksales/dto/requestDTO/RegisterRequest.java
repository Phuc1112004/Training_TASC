package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate createAt;
}
