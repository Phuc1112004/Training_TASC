package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
