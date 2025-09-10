package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
