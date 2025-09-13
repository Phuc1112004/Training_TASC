package com.example.specificationpaging;

import com.example.specificationpaging.entities.Order;
import com.example.specificationpaging.dto.OrderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT new com.example.specificationpaging.dto.OrderInfoDTO(o.id, o.customer.name, b.title, o.total) " +
            "FROM Order o JOIN o.books b " +
            "WHERE (:customerName IS NULL OR o.customer.name LIKE %:customerName%) " +
            "AND (:minTotal IS NULL OR o.total >= :minTotal)")
    List<OrderInfoDTO> findOrderInfoWithJoin(String customerName, Long minTotal);
}
