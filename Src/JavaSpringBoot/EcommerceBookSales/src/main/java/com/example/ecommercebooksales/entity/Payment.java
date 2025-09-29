package com.example.ecommercebooksales.entity;


import com.example.ecommercebooksales.enums.PaymentMethod;
import com.example.ecommercebooksales.enums.PaymentStatus;
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

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // COD, VNPay, Momo, Paypal...
    private Long amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // pending, completed, failed
    private LocalDateTime paidAt;
}
