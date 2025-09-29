package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.dto.requestDTO.PaymentRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.PaymentResponseDTO;
import com.example.ecommercebooksales.enums.PaymentStatus;
import com.example.ecommercebooksales.service.PaymentService;
import com.example.ecommercebooksales.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final VNPayService vnPayService;

    // 1. Tạo thanh toán (VNPay, Momo...)
    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDTO req) {
        String paymentUrl = String.valueOf(paymentService.createPayment(req));
        return ResponseEntity.ok(paymentUrl);
    }

    // 3. Lấy danh sách tất cả payment
    @GetMapping
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
