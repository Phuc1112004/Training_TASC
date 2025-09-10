package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Data;

@Data
public class PurchaseItemRequestDTO {
    private Long bookId;        // id sách nhập
    private Integer quantity;   // số lượng nhập
    private Long unitPrice;     // giá nhập tại thời điểm mua
}
