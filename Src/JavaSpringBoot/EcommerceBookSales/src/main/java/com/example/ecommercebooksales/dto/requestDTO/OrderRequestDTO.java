package com.example.ecommercebooksales.dto.requestDTO;

import com.example.ecommercebooksales.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequestDTO {
    private Long userId;  // người đặt hàng
    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    private String shippingAddress;   // địa chỉ ship
    @NotEmpty(message = "Đơn hàng phải có ít nhất một sản phẩm")
    @Valid
    private List<OrderItemResquestDTO> listOrderItems;   // danh sách + số lượng
}
