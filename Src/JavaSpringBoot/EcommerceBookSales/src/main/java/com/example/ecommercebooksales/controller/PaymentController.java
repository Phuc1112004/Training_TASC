package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.dto.requestDTO.PaymentRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.PaymentResponseDTO;
import com.example.ecommercebooksales.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }
}
