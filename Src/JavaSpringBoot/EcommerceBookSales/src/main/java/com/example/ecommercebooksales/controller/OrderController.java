package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.dto.requestDTO.OrderRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.OrderResponseDTO;
import com.example.ecommercebooksales.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Tạo đơn hàng
    @PostMapping
    public OrderResponseDTO createOrders(@RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }

    // Lấy tất cả đơn hàng
    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Lấy chi tiết đơn hàng theo ID
    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    // Cập nhật trạng thái đơn hàng
    @PutMapping("/{orderId}/status")
    public OrderResponseDTO updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status
    ) {
        return orderService.updateOrderStatus(orderId, status);
    }
}
