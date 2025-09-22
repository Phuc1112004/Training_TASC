package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Items;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Items, Long> {
    // Lấy tất cả item trong giỏ hàng của user
    List<Items> findByUsers_UserIdAndStatus(Long userId, Integer status);

    // Lấy tất cả item thuộc đơn hàng
    List<Items> findByOrders_OrderIdAndStatus(Long orderId, Integer status);

    Optional<Items> findByItemIdAndStatus(Long itemId, Integer status);

}
