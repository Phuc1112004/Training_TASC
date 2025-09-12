package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.dto.requestDTO.PurchaseItemRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.PurchaseItemResponseDTO;
import com.example.ecommercebooksales.service.PurchaseItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-items")
@RequiredArgsConstructor
public class PurchaseItemController {

    private final PurchaseItemService purchaseItemService;

    // ---------------- CREATE ----------------
    @PostMapping
    public ResponseEntity<PurchaseItemResponseDTO> createPurchaseItem(
            @RequestBody @Valid PurchaseItemRequestDTO request) {
        PurchaseItemResponseDTO response = purchaseItemService.createPurchaseItem(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ---------------- READ (list by purchase) ----------------
    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<List<PurchaseItemResponseDTO>> getItemsByPurchase(
            @PathVariable Long purchaseId) {
        List<PurchaseItemResponseDTO> items = purchaseItemService.getItemsByPurchaseId(purchaseId);
        return ResponseEntity.ok(items);
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseItemResponseDTO> updatePurchaseItem(
            @PathVariable Long id,
            @RequestBody @Valid PurchaseItemRequestDTO request) {
        PurchaseItemResponseDTO updated = purchaseItemService.updatePurchaseItem(id, request);
        return ResponseEntity.ok(updated);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseItem(@PathVariable Long id) {
        boolean deleted = purchaseItemService.deletePurchaseItem(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

