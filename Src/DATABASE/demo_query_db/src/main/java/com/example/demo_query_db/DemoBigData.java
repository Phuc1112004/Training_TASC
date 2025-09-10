package com.example.demo_query_db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class DemoBigData implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DemoBigData(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoBigData.class, args);
    }

    @Override
    public void run(String... args) {
        int age = 30;
        String phonePrefix = "09";
        String city = "Hanoi";

        // =============================
        // Test SELECT trực tiếp
        // =============================
        long start1 = System.currentTimeMillis();
        List<Map<String, Object>> result1 = jdbcTemplate.queryForList(
                "SELECT * FROM users WHERE age = ? AND phone LIKE ? AND city = ?",
                age, phonePrefix + "%", city
        );
        long end1 = System.currentTimeMillis();
        System.out.println("SELECT query in App: " + (end1 - start1) + " ms, rows=" + result1.size());

        // =============================
        // Test CALL procedure
        // =============================
        long start2 = System.currentTimeMillis();
        List<Map<String, Object>> result2 = jdbcTemplate.queryForList(
                "CALL find_users(?, ?, ?)", age, phonePrefix, city
        );
        long end2 = System.currentTimeMillis();
        System.out.println("CALL procedure in App: " + (end2 - start2) + " ms, rows=" + result2.size());
    }
}
