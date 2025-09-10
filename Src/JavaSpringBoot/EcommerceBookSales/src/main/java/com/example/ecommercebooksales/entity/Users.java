package com.example.ecommercebooksales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
    private LocalDateTime create_at;

    @OneToMany(mappedBy = "users")
    private List<Cart> carts;

    @OneToMany(mappedBy = "users")
    private List<Orders> orders;

    @OneToMany(mappedBy = "users")
    private List<Review> reviews;

}
