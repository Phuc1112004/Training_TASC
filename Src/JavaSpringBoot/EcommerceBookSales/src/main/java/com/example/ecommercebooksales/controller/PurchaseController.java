package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.dto.requestDTO.PurchaseRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.PurchaseResponseDTO;
import com.example.ecommercebooksales.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> createPurchase(@RequestBody PurchaseRequestDTO request) {
        PurchaseResponseDTO createPurchase = purchaseService.createPurchase(request);
        return ResponseEntity.ok(createPurchase);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponseDTO>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.getAllPurchase());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> getBookById(@PathVariable Long id) {
        PurchaseResponseDTO book = purchaseService.getPurchaseById(id);
        if (book == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(book);
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> updateBook(@PathVariable Long id,
                                                      @RequestBody PurchaseRequestDTO request) {
        PurchaseResponseDTO updatedBook = purchaseService.updatePurchase(id, request);
        if (updatedBook == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedBook);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = purchaseService.deletePurhchase(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build(); // 204
    }
}
