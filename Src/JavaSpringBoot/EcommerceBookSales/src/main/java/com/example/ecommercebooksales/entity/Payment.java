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
    private Long payment_id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    private String payment_method; // COD, VNPay, Momo, Paypal...
    private Long amount;
    private String status; // pending, completed, failed
    private LocalDateTime paidAt;
}
