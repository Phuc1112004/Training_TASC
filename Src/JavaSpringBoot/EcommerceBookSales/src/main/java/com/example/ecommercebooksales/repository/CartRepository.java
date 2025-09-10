package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
