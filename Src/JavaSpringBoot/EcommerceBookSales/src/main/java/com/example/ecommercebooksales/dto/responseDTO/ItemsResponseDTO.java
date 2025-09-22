package com.example.ecommercebooksales.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsResponseDTO {
    private Long itemId;
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private Long price;
    private Integer status; // 0 = cart, 1 = order
    private Long orderId;   // null nếu status = 0
}
