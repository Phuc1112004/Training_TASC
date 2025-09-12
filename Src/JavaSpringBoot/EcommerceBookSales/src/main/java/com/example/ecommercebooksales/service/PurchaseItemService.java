package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.PurchaseItemRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.PurchaseItemResponseDTO;
import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.Purchase;
import com.example.ecommercebooksales.entity.PurchaseItem;
import com.example.ecommercebooksales.repository.BookRepository;
import com.example.ecommercebooksales.repository.PurchaseItemRepository;
import com.example.ecommercebooksales.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseItemService {

    private final PurchaseItemRepository purchaseItemRepository;
    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;

    public PurchaseItemService(PurchaseItemRepository purchaseItemRepository,
                               PurchaseRepository purchaseRepository,
                               BookRepository bookRepository) {
        this.purchaseItemRepository = purchaseItemRepository;
        this.purchaseRepository = purchaseRepository;
        this.bookRepository = bookRepository;
    }

    // ---------------- CREATE ----------------
    public PurchaseItemResponseDTO createPurchaseItem(PurchaseItemRequestDTO request) {
        Purchase purchase = purchaseRepository.findById(request.getPurchaseId())
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
        Books book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        PurchaseItem item = new PurchaseItem();
        item.setPurchase(purchase);
        item.setBooks(book);
        item.setQuantity(request.getQuantity());
        item.setUnitPrice(request.getUnitPrice());

        PurchaseItem saved = purchaseItemRepository.save(item);
        return convertToDTO(saved);
    }

    // ---------------- READ ----------------
    public List<PurchaseItemResponseDTO> getItemsByPurchaseId(Long purchaseId) {
        return purchaseItemRepository.findByPurchase_PurchaseId(purchaseId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------------- UPDATE ----------------
    public PurchaseItemResponseDTO updatePurchaseItem(Long id, PurchaseItemRequestDTO request) {
        return purchaseItemRepository.findById(id)
                .map(item -> {
                    Books book = bookRepository.findById(request.getBookId())
                            .orElseThrow(() -> new RuntimeException("Book not found"));
                    item.setBooks(book);
                    item.setQuantity(request.getQuantity());
                    item.setUnitPrice(request.getUnitPrice());
                    PurchaseItem updated = purchaseItemRepository.save(item);
                    return convertToDTO(updated);
                }).orElseThrow(() -> new RuntimeException("PurchaseItem not found"));
    }

    // ---------------- DELETE ----------------
    public boolean deletePurchaseItem(Long id) {
        if (!purchaseItemRepository.existsById(id)) return false;
        purchaseItemRepository.deleteById(id);
        return true;
    }

    // ---------------- CONVERT ----------------
    private PurchaseItemResponseDTO convertToDTO(PurchaseItem item) {
        PurchaseItemResponseDTO dto = new PurchaseItemResponseDTO();
        dto.setPurchaseItemId(item.getPurchaseItemId());
        dto.setBookId(item.getBooks().getBookId());
        dto.setBookTitle(item.getBooks().getTitle());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setSubtotal(item.getQuantity() * item.getUnitPrice());
        return dto;
    }
}

