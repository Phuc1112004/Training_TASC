package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.PaymentRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.PaymentResponseDTO;
import com.example.ecommercebooksales.entity.Orders;
import com.example.ecommercebooksales.entity.Payment;
import com.example.ecommercebooksales.enums.OrderStatus;
import com.example.ecommercebooksales.enums.PaymentMethod;
import com.example.ecommercebooksales.enums.PaymentStatus;
import com.example.ecommercebooksales.repository.OrderRepository;
import com.example.ecommercebooksales.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public List<PaymentResponseDTO> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        System.out.println("PaymentRequestDTO.paymentMethod = [" + request.getPaymentMethod() + "]");

        Orders order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrders(order);

        // Xử lý PaymentMethod an toàn
        String methodStr = request.getPaymentMethod();
        PaymentMethod paymentMethod;
        try {
            paymentMethod = PaymentMethod.valueOf(methodStr.toUpperCase()); // chuyển về chữ hoa
        } catch (Exception e) {
            paymentMethod = PaymentMethod.VNPAY; // default nếu không hợp lệ
        }
        payment.setPaymentMethod(paymentMethod);

        payment.setAmount(request.getAmount());
        payment.setStatus(PaymentStatus.PENDING); // mặc định pending
        payment.setPaidAt(null);

        Payment saved = paymentRepository.save(payment);
        return convertToResponseDTO(saved);
    }


    public void updatePaymentStatus(Long orderId, String status) {
        Payment payment = paymentRepository.findByOrders_OrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Orders order = payment.getOrders();

        if ("COMPLETED".equals(status)) {
            payment.setStatus(PaymentStatus.COMPLETED);
            payment.setPaidAt(LocalDateTime.now());
            order.setStatus(OrderStatus.PAID);
        } else if ("FAILED".equals(status)) {
            payment.setStatus(PaymentStatus.FAILED);
            if (order.getStatus() == OrderStatus.PENDING) {
                order.setStatus(OrderStatus.PENDING);
            }
        }

        orderRepository.save(order);
        paymentRepository.save(payment);
    }

    public PaymentResponseDTO savePayment(Payment payment) {
        Payment savedPayment = paymentRepository.save(payment);
        return convertToResponseDTO(savedPayment);
    }

    public PaymentResponseDTO createOrGetVnpayPayment(Orders order) {
        Payment payment = paymentRepository.findByOrders_OrderId(order.getOrderId())
                .orElseGet(() -> {
                    Payment p = new Payment();
                    p.setOrders(order);
                    p.setAmount(order.getTotalAmount());
                    p.setStatus(PaymentStatus.PENDING);
                    p.setPaymentMethod(PaymentMethod.VNPAY); // chỉ VNPAY
                    return paymentRepository.save(p);
                });
        return convertToResponseDTO(payment);
    }




    private PaymentResponseDTO convertToResponseDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setOrderId(payment.getOrders().getOrderId());
        dto.setPaymentMethod(payment.getPaymentMethod().toString());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus().toString());
        dto.setPaidAt(payment.getPaidAt());
        return dto;
    }
}

