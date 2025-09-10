package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
