package com.example.ecommercebooksales.entity;

import com.example.ecommercebooksales.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long totalAmount;
    private String shippingAddress;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Items> items;

    @OneToMany(mappedBy = "orders")
    private List<Payment> payment;
}
