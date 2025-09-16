package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long orderId;
    private String paymentMethod;
    private Long amount;
}
