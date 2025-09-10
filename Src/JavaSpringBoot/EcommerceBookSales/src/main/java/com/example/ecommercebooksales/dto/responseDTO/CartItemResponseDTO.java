package com.example.ecommercebooksales.dto.responseDTO;

import lombok.Data;

@Data
public class CartItemResponseDTO {
    private Long cartItemId;
    private Long bookId;
    private String bookTitle; // tùy chọn để hiển thị
    private Long price;       // giá bán hiện tại
    private Integer quantity;
}
