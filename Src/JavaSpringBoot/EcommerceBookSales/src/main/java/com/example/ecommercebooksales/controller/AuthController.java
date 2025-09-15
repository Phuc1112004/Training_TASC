package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.config.JwtUtil;
import com.example.ecommercebooksales.dto.requestDTO.LoginRequest;
import com.example.ecommercebooksales.dto.requestDTO.RegisterRequest;
import com.example.ecommercebooksales.dto.responseDTO.LoginResponse;
import com.example.ecommercebooksales.dto.responseDTO.RegisterResponse;
import com.example.ecommercebooksales.entity.Users;
import com.example.ecommercebooksales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new RegisterResponse(
                            LocalDateTime.now(),
                            HttpStatus.BAD_REQUEST.value(),
                            "Username already exists!",
                            null
                    ));
        }

        Users user = new Users();
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole("CUSTOMER"); // mặc định role CUSTOMER
        user.setCreateAt(LocalDateTime.now());

        Users savedUser = userRepository.save(user);

        RegisterResponse.UserData userData = new RegisterResponse.UserData(
                savedUser.getUserId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getRole()
        );

        RegisterResponse response = new RegisterResponse(
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Đăng ký thành công",
                userData
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        LoginResponse response = new LoginResponse(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Đăng nhập thành công",
                accessToken,
                refreshToken
        );

        return ResponseEntity.ok(response);
    }


    // Refresh token
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Refresh token is required"));
        }

        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid or expired refresh token"));
        }

        String username = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = userRepository.findByUserName(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUserName())
                        .password(user.getPassword())
                        .roles(user.getRole()) // phải có prefix ROLE_ trong JwtAuthFilter
                        .build())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtUtil.generateAccessToken(userDetails);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}
