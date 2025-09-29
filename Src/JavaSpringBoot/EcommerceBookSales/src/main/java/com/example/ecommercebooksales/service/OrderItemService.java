package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.OrderItemResquestDTO;
import com.example.ecommercebooksales.dto.responseDTO.OrderItemResponseDTO;
import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.OrderItem;
import com.example.ecommercebooksales.entity.Orders;
import com.example.ecommercebooksales.exception.ResourceNotFoundException;
import com.example.ecommercebooksales.repository.BookRepository;
import com.example.ecommercebooksales.repository.OrderItemRepository;
import com.example.ecommercebooksales.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    // ----------------- Thêm OrderItem -----------------
    @Transactional
    public OrderItemResponseDTO createOrderItem(OrderItemResquestDTO request) {
        Orders order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order không tồn tại"));

        Books book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book không tồn tại"));

        if (book.getStockQuantity() < request.getQuantity()) {
            throw new RuntimeException("Không đủ số lượng tồn kho cho sách: " + book.getTitle());
        }

        book.setStockQuantity(book.getStockQuantity() - request.getQuantity());

        OrderItem item = new OrderItem();
        item.setOrders(order);
        item.setBooks(book);
        item.setQuantity(request.getQuantity());
        item.setPrice(request.getPrice() != null ? request.getPrice() : book.getSalePrice());

        OrderItem saved = orderItemRepository.save(item);
        return mapToDTO(saved);
    }

    // ----------------- Lấy tất cả item theo Order -----------------
    public List<OrderItemResponseDTO> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrders_OrderId(orderId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ----------------- Cập nhật số lượng -----------------
    @Transactional
    public OrderItemResponseDTO updateOrderItem(Long itemId, int quantity) {
        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem không tồn tại"));

        Books book = item.getBooks();
        int diff = quantity - item.getQuantity();

        if (diff > 0 && book.getStockQuantity() < diff) {
            throw new RuntimeException("Không đủ số lượng tồn kho để cập nhật");
        }

        book.setStockQuantity(book.getStockQuantity() - diff);
        item.setQuantity(quantity);

        return mapToDTO(orderItemRepository.save(item));
    }

    // ----------------- Xóa OrderItem -----------------
    @Transactional
    public void deleteOrderItem(Long itemId) {
        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem không tồn tại"));

        // trả lại stock
        Books book = item.getBooks();
        book.setStockQuantity(book.getStockQuantity() + item.getQuantity());

        orderItemRepository.delete(item);
    }

    // ----------------- Map entity → DTO -----------------
    private OrderItemResponseDTO mapToDTO(OrderItem item) {
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setOrderItemId(item.getOrderItemId());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setOrderId(item.getOrders() != null ? item.getOrders().getOrderId() : null);

        if (item.getBooks() != null) {
            dto.setBookId(item.getBooks().getBookId());
            dto.setBookTitle(item.getBooks().getTitle());
        }
        return dto;
    }
}
