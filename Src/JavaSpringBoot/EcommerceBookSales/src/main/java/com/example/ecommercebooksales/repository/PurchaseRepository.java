package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Page<Purchase> findByPurchaseDateBetween(LocalDate start, LocalDate end, Pageable pageable);
}
