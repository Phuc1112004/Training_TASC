package com.example.demo_query_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void testQuery() {
        // Query ở tầng Application
        long start1 = System.currentTimeMillis();
        List<Map<String, Object>> results1 = jdbcTemplate.queryForList(
                "SELECT * FROM users WHERE age = ? AND phone LIKE ? AND city = ?",
                30, "090123%", "City5"
        );
        long end1 = System.currentTimeMillis();
        System.out.println("App query time: " + (end1 - start1) + " ms, results=" + results1.size());

        // Gọi Stored Procedure qua SimpleJdbcCall
        long start2 = System.currentTimeMillis();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("find_users");

        Map<String, Object> params = new HashMap<>();
        params.put("p_age", 30);
        params.put("p_phone_prefix", "090123");
        params.put("p_city", "City5");

        Map<String, Object> result = jdbcCall.execute(params);
        long end2 = System.currentTimeMillis();

        // kết quả trả về trong key "#result-set-1"
        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("#result-set-1");

        System.out.println("Stored procedure time: " + (end2 - start2) + " ms, results=" + rows.size());
    }
}

