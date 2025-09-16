package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Data;

@Data
public class CartItemRequestDTO {
    private Long cartId;
    private Long bookId;
    private Integer quantity;

}
