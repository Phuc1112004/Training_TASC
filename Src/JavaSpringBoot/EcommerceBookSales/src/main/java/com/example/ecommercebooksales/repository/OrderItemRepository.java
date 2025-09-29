package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrders_OrderId(Long orderId);
    List<OrderItem> findAllByBooksIn(List<Books> books);
}
