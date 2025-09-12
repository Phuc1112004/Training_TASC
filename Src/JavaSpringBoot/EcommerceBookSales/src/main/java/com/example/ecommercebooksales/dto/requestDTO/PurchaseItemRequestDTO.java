package com.example.ecommercebooksales.dto.requestDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PurchaseItemRequestDTO {
    @NotNull
    private Long purchaseId;
    @NotNull
    private Long bookId;        // id sách nhập
    @Positive
    private Integer quantity;   // số lượng nhập
    @PositiveOrZero
    private Long unitPrice;     // giá nhập tại thời điểm mua
}
