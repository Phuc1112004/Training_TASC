package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequestDTO {
    private Long userId;  // người đặt hàng
    private String shippingAddress;   // địa chỉ ship
    private Long totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemResquestDTO> listOrderItems;   // danh sách + số lượng
}
