package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.PurchaseRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.BookResponseDTO;
import com.example.ecommercebooksales.dto.responseDTO.PurchaseItemResponseDTO;
import com.example.ecommercebooksales.dto.responseDTO.PurchaseResponseDTO;
import com.example.ecommercebooksales.entity.Purchase;
import com.example.ecommercebooksales.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
    private PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    // ---------------- CREATE ----------------
    public PurchaseResponseDTO createPurchase(PurchaseRequestDTO request) {
        Purchase purchase = new Purchase();
        purchase.setSupplier_name(request.getSupplierName());
        purchase.setPurchase_date(request.getPurchaseDate());

        Purchase saved = purchaseRepository.save(purchase);
        return convertToDTO(saved);
    }

    // ---------------- READ ----------------
    public List<PurchaseResponseDTO> getAllPurchase() {
        return purchaseRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    // ---------------- FIND BY ID ----------------
    public PurchaseResponseDTO getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // ---------------- UPDATE ----------------
    public PurchaseResponseDTO updatePurchase(Long id, PurchaseRequestDTO request) {
        return purchaseRepository.findById(id)
                .map(purchase ->{
                   purchase.setSupplier_name(request.getSupplierName());
                   purchase.setPurchase_date(request.getPurchaseDate());

                   Purchase updated = purchaseRepository.save(purchase);
                   return convertToDTO(updated);
                }).orElse(null);
    }

    // ---------------- DELETE ----------------
    public boolean deletePurhchase(Long id) {
        if (!purchaseRepository.existsById(id)) return false;
        purchaseRepository.deleteById(id);
        return true;
    }

    // ---------------- CONVERT ----------------
    private PurchaseResponseDTO convertToDTO(Purchase purchase){
        PurchaseResponseDTO dto = new PurchaseResponseDTO();
        dto.setPurchaseId(purchase.getPurchase_id());
        dto.setSupplierName(purchase.getSupplier_name());
        dto.setPurchaseDate(purchase.getPurchase_date());
        dto.setTotalCost(purchase.getTotal_cost());
        return dto;
    }
}
