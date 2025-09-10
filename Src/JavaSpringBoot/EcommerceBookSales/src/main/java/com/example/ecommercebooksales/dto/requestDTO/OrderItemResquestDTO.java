package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Data;

@Data
public class OrderItemResquestDTO {
    private Long bookId;
    private Integer quantity;
}
