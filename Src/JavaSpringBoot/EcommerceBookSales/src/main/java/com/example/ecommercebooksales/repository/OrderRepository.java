package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Orders;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Override
    @EntityGraph(attributePaths = "orderItems")
    List<Orders> findAll();
}
