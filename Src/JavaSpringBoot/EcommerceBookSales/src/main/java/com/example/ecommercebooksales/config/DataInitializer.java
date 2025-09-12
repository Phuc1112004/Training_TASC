package com.example.ecommercebooksales.config;

import com.example.ecommercebooksales.entity.Users;
import com.example.ecommercebooksales.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Nếu chưa có admin thì tạo mới
        if (userRepository.findByUsername("admin").isEmpty()) {
            Users admin = new Users();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin")); // mật khẩu mặc định: admin
            admin.setEmail("admin@gmail.com");
            admin.setPhone("0123456789");
            admin.setRole("ADMIN");
            admin.setCreateAt(LocalDateTime.now());

            userRepository.save(admin);

            System.out.println("✅ Admin account created: username=admin, password=admin");
        }
    }
}
