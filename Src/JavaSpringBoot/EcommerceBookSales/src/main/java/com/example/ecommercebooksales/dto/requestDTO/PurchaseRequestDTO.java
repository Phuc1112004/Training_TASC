package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PurchaseRequestDTO {
    private String supplierName;                 // tên nhà cung cấp
    private LocalDate purchaseDate;          // ngày nhập hàng
    private List<PurchaseItemRequestDTO> listPurchaseItems;  // danh sách sách + số lượng + giá nhập
}
