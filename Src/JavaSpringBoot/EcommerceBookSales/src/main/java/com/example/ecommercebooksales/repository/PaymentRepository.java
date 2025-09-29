package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.Orders;
import com.example.ecommercebooksales.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrders(Orders orders);
    Optional<Payment> findByOrders_OrderId(Long orderId);  //Optional khác với list là optional chỉ có 0 hoặc 1 dữ liệu tránh NullPointerException khi dữ liệu ko tồn tại
}
