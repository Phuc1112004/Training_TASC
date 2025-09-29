package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Orders;
import com.example.ecommercebooksales.enums.OrderStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Override
    @EntityGraph(attributePaths = "listOrderItems")
    List<Orders> findAll();

    @Query("SELECT o FROM Orders o " +
            "WHERE (:keyword IS NULL OR " +
            "      o.users.phone LIKE %:keyword% OR " +
            "      o.users.userName LIKE %:keyword% OR " +
            "      o.users.email LIKE %:keyword%) " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:dateFrom IS NULL OR o.createdAt >= :dateFrom) " +
            "AND (:dateTo IS NULL OR o.createdAt <= :dateTo)")
    List<Orders> searchOrders(@Param("keyword") String keyword,
                              @Param("status") OrderStatus status,
                              @Param("dateFrom") LocalDateTime dateFrom,
                              @Param("dateTo") LocalDateTime dateTo);
}
