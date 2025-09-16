package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.PurchaseItemRequestDTO;
import com.example.ecommercebooksales.dto.requestDTO.PurchaseRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.PurchaseItemResponseDTO;
import com.example.ecommercebooksales.dto.responseDTO.PurchaseResponseDTO;
import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.Purchase;
import com.example.ecommercebooksales.entity.PurchaseItem;
import com.example.ecommercebooksales.exception.ResourceNotFoundException;
import com.example.ecommercebooksales.repository.BookRepository;
import com.example.ecommercebooksales.repository.PurchaseItemRepository;
import com.example.ecommercebooksales.repository.PurchaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;
    private final PurchaseItemRepository purchaseItemRepository;

    public PurchaseService(PurchaseRepository purchaseRepository,
                           BookRepository bookRepository,
                           PurchaseItemRepository purchaseItemRepository) {
        this.purchaseRepository = purchaseRepository;
        this.bookRepository = bookRepository;
        this.purchaseItemRepository = purchaseItemRepository;
    }

    // ---------------- CREATE ----------------
    @Transactional
    public PurchaseResponseDTO createPurchase(PurchaseRequestDTO request) {
        Purchase purchase = new Purchase();
        purchase.setSupplierName(request.getSupplierName());
        purchase.setPurchaseDate(request.getPurchaseDate());

        // Khởi tạo list items
        List<PurchaseItem> items = new ArrayList<>();
        long totalCost = 0L;

        if (request.getListPurchaseItems() != null && !request.getListPurchaseItems().isEmpty()) {
            for (PurchaseItemRequestDTO itemReq : request.getListPurchaseItems()) {
                Books book = bookRepository.findById(itemReq.getBookId())
                        .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + itemReq.getBookId()));

                PurchaseItem item = new PurchaseItem();
                item.setPurchase(purchase); // set parent
                item.setBooks(book);
                item.setQuantity(itemReq.getQuantity());
                item.setUnitPrice(itemReq.getUnitPrice());

                items.add(item);

                // Cập nhật stock
                book.setStockQuantity(book.getStockQuantity() + itemReq.getQuantity());
                bookRepository.save(book);

                totalCost += itemReq.getQuantity() * itemReq.getUnitPrice();
            }
        }
        // Gán items cho purchase
        purchase.setItems(items);
        purchase.setTotalCost(totalCost);

        // Lưu Purchase và cascade lưu PurchaseItem
        Purchase savedPurchase = purchaseRepository.save(purchase);

        return convertToDTO(savedPurchase);
    }


    // ---------------- READ ----------------
    public Page<PurchaseResponseDTO> getAllPurchase(Pageable pageable) {
        return purchaseRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public PurchaseResponseDTO getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id: " + id));
    }

    // ---------------- UPDATE ----------------
    @Transactional
    public PurchaseResponseDTO updatePurchase(Long id, PurchaseRequestDTO request) {
        return purchaseRepository.findById(id)
                .map(purchase -> {
                    purchase.setSupplierName(request.getSupplierName());
                    purchase.setPurchaseDate(request.getPurchaseDate());
                    return convertToDTO(purchaseRepository.save(purchase));
                }).orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id: " + id));
    }

    // ---------------- DELETE ----------------
    @Transactional
    public boolean deletePurchase(Long id) {
        if (!purchaseRepository.existsById(id)) return false;
        purchaseRepository.deleteById(id);
        return true;
    }

    // ---------------- CONVERT ----------------
    private PurchaseResponseDTO convertToDTO(Purchase purchase) {
        PurchaseResponseDTO dto = new PurchaseResponseDTO();
        dto.setPurchaseId(purchase.getPurchaseId());
        dto.setSupplierName(purchase.getSupplierName());
        dto.setPurchaseDate(purchase.getPurchaseDate());
        dto.setTotalCost(purchase.getTotalCost() != null ? purchase.getTotalCost() : 0L);

        if (purchase.getItems() != null && !purchase.getItems().isEmpty()) {
            List<PurchaseItemResponseDTO> itemDTOs = purchase.getItems()
                    .stream()
                    .map(item -> {
                        PurchaseItemResponseDTO iDto = new PurchaseItemResponseDTO();
                        iDto.setPurchaseItemId(item.getPurchaseItemId());
                        iDto.setBookId(item.getBooks().getBookId());
                        iDto.setBookTitle(item.getBooks().getTitle());
                        iDto.setQuantity(item.getQuantity());
                        iDto.setUnitPrice(item.getUnitPrice());
                        iDto.setSubtotal(item.getQuantity() * item.getUnitPrice());
                        return iDto;
                    }).collect(Collectors.toList());
            dto.setListPurchaseItems(itemDTOs);
        }
        return dto;
    }

    // ---------------- SEARCH BY DATE WITH PAGING ----------------
    public Page<PurchaseResponseDTO> searchByDate(LocalDate start, LocalDate end, Pageable pageable) {
        if (start == null || end == null)
            throw new IllegalArgumentException("Start date và End date không được để trống");
        if (end.isBefore(start))
            throw new IllegalArgumentException("End date phải lớn hơn hoặc bằng Start date");

        Page<Purchase> pageResult = purchaseRepository.findByPurchaseDateBetween(start, end, pageable);
        if (pageResult.isEmpty())
            throw new ResourceNotFoundException("Không tìm thấy đơn nhập hàng trong khoảng thời gian này");

        return pageResult.map(this::convertToDTO);
    }
}
