package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.OrderItemResquestDTO;
import com.example.ecommercebooksales.dto.responseDTO.OrderItemResponseDTO;
import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.OrderItem;
import com.example.ecommercebooksales.entity.Orders;
import com.example.ecommercebooksales.repository.BookRepository;
import com.example.ecommercebooksales.repository.OrderItemRepository;
import com.example.ecommercebooksales.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    public OrderItemResponseDTO createOrderItem(OrderItemResquestDTO request) {
        // 1️⃣ Kiểm tra dữ liệu đầu vào
        if (request.getOrderId() == null) {
            throw new RuntimeException("Order ID must not be null");
        }
        if (request.getBookId() == null) {
            throw new RuntimeException("Book ID must not be null");
        }

        // 2️⃣ Lấy Order và Book từ DB
        Orders order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Books book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // 3️⃣ Tạo OrderItem mới
        OrderItem item = new OrderItem();
        item.setOrders(order);
        item.setBooks(book);
        item.setQuantity(request.getQuantity() != null ? request.getQuantity() : 1);
        item.setPrice(request.getPrice() != null ? request.getPrice() : book.getSalePrice());

        // 4️⃣ Lưu OrderItem
        OrderItem savedItem = orderItemRepository.save(item);

        // 5️⃣ Cập nhật tổng tiền của Order
        List<OrderItem> currentItems = order.getOrderItems();
        if (currentItems == null) {
            currentItems = new ArrayList<>();
        }
        currentItems.add(savedItem);
        order.setOrderItems(currentItems);

        long total = currentItems.stream()
                .mapToLong(i -> i.getPrice() * i.getQuantity())
                .sum();
        order.setTotalAmount(total);

        // 6️⃣ Lưu lại Order với tổng tiền mới
        orderRepository.save(order);

        // 7️⃣ Trả về DTO
        return convertToDTO(savedItem);
    }


    public List<OrderItemResponseDTO> getItemsByOrderId(Long orderId) {
        List<OrderItem> items = orderItemRepository.findByOrders_OrderId(orderId);
        return items.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public void deleteOrderItem(Long orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new RuntimeException("Order item not found");
        }
        orderItemRepository.deleteById(orderItemId);
    }

    public OrderItemResponseDTO updateOrderItemQuantity(Long orderItemId, int quantity) {
        OrderItem item = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));
        item.setQuantity(quantity);
        OrderItem updated = orderItemRepository.save(item);
        return convertToDTO(updated);
    }

    private OrderItemResponseDTO convertToDTO(OrderItem orderItem) {
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setOrderItemId(orderItem.getOrderItemId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());

        return dto;
    }
}
