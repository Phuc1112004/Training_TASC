package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.PaymentRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.PaymentResponseDTO;
import com.example.ecommercebooksales.entity.Orders;
import com.example.ecommercebooksales.entity.Payment;
import com.example.ecommercebooksales.repository.OrderRepository;
import com.example.ecommercebooksales.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        Orders order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrders(order);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount());
        payment.setStatus("pending"); // mặc định pending
        payment.setPaidAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);

        return convertToResponseDTO(saved);
    }

    private PaymentResponseDTO convertToResponseDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setOrderId(payment.getOrders().getOrderId());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setPaidAt(payment.getPaidAt());
        return dto;
    }
}

