package com.example.ecommercebooksales.dto.responseDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private Long userId;
    private String userName;
    private Long totalAmount;
    private String status;               // pending, paid, shipped...
    private String shippingAddress;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDTO> listOrderItems; // chi tiết từng sách

}
