//package com.example.ecommercebooksales.repository;
//
//import com.example.ecommercebooksales.entity.OrderItem;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ItemRepository extends JpaRepository<OrderItem, Long> {
//    // Lấy tất cả item trong giỏ hàng của user
//    List<OrderItem> findByUsers_UserIdAndStatus(Long userId, Integer status);
//
//    // Lấy tất cả item thuộc đơn hàng
//    List<OrderItem> findByOrders_OrderIdAndStatus(Long orderId, Integer status);
//
//    Optional<OrderItem> findByItemIdAndStatus(Long itemId, Integer status);
//
//}
