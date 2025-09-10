package com.example.ecommercebooksales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coupon_id;

    private String code;
    private Integer discount_percent;

    @Column(precision = 10, scale = 2)
    private BigDecimal max_discount;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String status; // active, expired
}
