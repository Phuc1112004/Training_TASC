package com.example.demo_springboot;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // ✅ Data static (giả lập database)
    private List<Users> users = new ArrayList<>(Arrays.asList(
            new Users(1, "Phúc", "phuc@example.com"),
            new Users(2, "Lan", "lan@example.com"),
            new Users(3, "Nam", "nam@example.com")
    ));

    // ====== GET ALL USERS (dùng query param phân trang) ======
    @GetMapping
    public List<Users> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return users.subList(0, Math.min(size, users.size())); // fake paging
    }

    // ====== GET ONE USER (dùng path variable) ======
    @GetMapping("/{id}")
    public Users getUserById(@PathVariable int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst()
                .orElse(null);
    }

    // ====== CREATE USER (dùng request body) ======
    @PostMapping
    public String createUser(@RequestBody Users user) {
        users.add(user);
        return "User created: " + user.getName();
    }

    // ====== UPDATE USER (dùng path + body) ======
    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody Users updatedUser) {
        for (Users u : users) {
            if (u.getId() == id) {
                u.setName(updatedUser.getName());
                u.setEmail(updatedUser.getEmail());
                return "User updated: " + u.getName();
            }
        }
        return "User not found";
    }

    // ====== DELETE USER (dùng path variable) ======
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        users.removeIf(u -> u.getId() == id);
        return "User deleted with id " + id;
    }

    // ====== GET INFO (dùng header + cookie) ======
    @GetMapping("/info")
    public String getInfo(@RequestHeader("User-Agent") String userAgent,
                          @CookieValue(value = "sessionId", defaultValue = "none") String sessionId) {
        return "User-Agent: " + userAgent + " | SessionId: " + sessionId;
    }
}

