package com.example.ecommercebooksales.dto.responseDTO;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long orderItemId;
    private Long orderId;
    private Long bookId;
    private String bookTitle;   // để client hiển thị
    private Long price;         // giá bán lúc tạo order
    private Integer quantity;
}
