package com.example.ecommercebooksales.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    private String paymentMethod; // COD, VNPay, Momo, Paypal...
    private Long amount;
    private String status; // pending, completed, failed
    private LocalDateTime paidAt;
}
