package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
