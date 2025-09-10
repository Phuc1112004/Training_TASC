package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
