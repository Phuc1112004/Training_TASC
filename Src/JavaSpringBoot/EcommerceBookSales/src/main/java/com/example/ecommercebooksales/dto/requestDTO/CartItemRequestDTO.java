package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Data;

@Data
public class CartItemRequestDTO {
    private String bookId;
    private Integer quantity;

}
