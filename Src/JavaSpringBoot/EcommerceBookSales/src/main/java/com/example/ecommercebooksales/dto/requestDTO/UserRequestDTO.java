package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String role;
}
