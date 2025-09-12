package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
    List<PurchaseItem> findByPurchase_PurchaseId(Long purchaseId);
}
