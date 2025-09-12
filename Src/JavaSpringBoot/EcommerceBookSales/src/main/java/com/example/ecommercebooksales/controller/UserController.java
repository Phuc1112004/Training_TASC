package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.entity.Users;
import com.example.ecommercebooksales.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lấy tất cả user
    @GetMapping
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
}

