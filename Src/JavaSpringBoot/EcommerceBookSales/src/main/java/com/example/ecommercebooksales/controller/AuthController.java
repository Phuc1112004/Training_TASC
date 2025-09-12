package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.config.JwtUtil;
import com.example.ecommercebooksales.dto.requestDTO.LoginRequest;
import com.example.ecommercebooksales.dto.requestDTO.RegisterRequest;
import com.example.ecommercebooksales.dto.responseDTO.LoginResponse;
import com.example.ecommercebooksales.entity.Users;
import com.example.ecommercebooksales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Đăng ký
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole("CUSTOMER"); // mặc định role CUSTOMER
        user.setCreateAt(LocalDateTime.now());

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Sinh cả accessToken và refreshToken
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
    }

    // Refresh token
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body("Refresh token is required");
        }

        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(401).body("Invalid or expired refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = userRepository.findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtUtil.generateAccessToken(userDetails);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}


