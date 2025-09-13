package com.example.ecommercebooksales.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private String code;
    private Integer discountPercent;

    @Column(precision = 10, scale = 2)
    private BigDecimal maxDiscount;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String status; // active, expired
}
