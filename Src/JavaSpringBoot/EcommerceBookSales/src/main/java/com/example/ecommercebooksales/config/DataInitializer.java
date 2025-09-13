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
        // ✅ ADMIN
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

        // ✅ CUSTOMER 1
        if (userRepository.findByUsername("customer").isEmpty()) {
            Users customer1 = new Users();
            customer1.setUsername("customer");
            customer1.setPassword(passwordEncoder.encode("123456"));
            customer1.setEmail("customer@gmail.com");
            customer1.setPhone("0987654321");
            customer1.setRole("CUSTOMER");
            customer1.setCreateAt(LocalDateTime.now());

            userRepository.save(customer1);
            System.out.println("✅ Customer account created: username=customer1, password=123456");
        }

    }
}
