package com.example.specificationpaging;

import com.example.specificationpaging.dto.OrderInfoDTO;
import com.example.specificationpaging.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    public Page<Order> getOrders(
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) Long minTotal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.getOrders(customerName, minTotal, page, size);
    }

    @GetMapping("/info")
    public List<OrderInfoDTO> getOrderInfo(
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) Long minTotal) {
        return orderService.getOrderInfo(customerName, minTotal);
    }
}
