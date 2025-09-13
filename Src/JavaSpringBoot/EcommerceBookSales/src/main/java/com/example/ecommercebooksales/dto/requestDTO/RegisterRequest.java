package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    // ❌ bỏ createAt vì backend tự set
}
