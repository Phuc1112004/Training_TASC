package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<Items, Long> {
    List<Items> findByOrders_OrderId(Long orderId);
    List<Items> findAllByBooksIn(List<Books> books);
}
