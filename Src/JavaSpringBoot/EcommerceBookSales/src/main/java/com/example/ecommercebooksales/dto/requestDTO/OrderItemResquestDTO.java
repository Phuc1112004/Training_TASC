package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderItemResquestDTO {
    private Long orderId;
    private Long bookId;
    private Integer quantity;
    private Long price;
}
