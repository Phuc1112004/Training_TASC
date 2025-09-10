package com.example.ecommercebooksales.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class CouponDTO {
    private String code;
    private Integer discount_percent;
    @Column(precision = 10, scale = 2)
    private BigDecimal max_discount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
