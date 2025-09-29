package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.OrderItemResquestDTO;
import com.example.ecommercebooksales.dto.requestDTO.OrderRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.OrderItemResponseDTO;
import com.example.ecommercebooksales.dto.responseDTO.OrderResponseDTO;
import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.OrderItem;
import com.example.ecommercebooksales.entity.Orders;
import com.example.ecommercebooksales.entity.Users;
import com.example.ecommercebooksales.enums.OrderStatus;
import com.example.ecommercebooksales.exception.ResourceNotFoundException;
import com.example.ecommercebooksales.repository.BookRepository;
import com.example.ecommercebooksales.repository.OrderRepository;
import com.example.ecommercebooksales.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    // Tạo đơn hàng mới
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Orders order = new Orders();
        order.setUsers(user);
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();
        long totalAmount = 0L;

        for (OrderItemResquestDTO itemReq : request.getListItems()) {
            Books book = bookRepository.findById(itemReq.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + itemReq.getBookId()));

            if (book.getStockQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Not enough stock for book: " + book.getTitle());
            }

            book.setStockQuantity(book.getStockQuantity() - itemReq.getQuantity());

            OrderItem item = new OrderItem();
            item.setOrders(order);
            item.setBooks(book);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(book.getSalePrice());

            items.add(item); // Thêm vào list
            totalAmount += book.getSalePrice() * itemReq.getQuantity();
        }

        order.setListOrderItems(items); // Gán list cho order
        order.setTotalAmount(totalAmount);

        Orders savedOrder = orderRepository.save(order);

        return convertToDTO(savedOrder);
    }



    // Lấy đơn hàng theo ID
    public OrderResponseDTO getOrderById(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    // Lấy tất cả đơn hàng
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Orders findById(Long orderId) {
        Optional<Orders> order = orderRepository.findById(orderId);
        return order.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public Orders save(Orders order){
        return orderRepository.save(order);
    }



    // Xóa đơn hàng
    public void deleteOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }

    // Cập nhật trạng thái đơn hàng
    public OrderResponseDTO updateOrderStatus(Long orderId, String status) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(order.getStatus());
        Orders updated = orderRepository.save(order);
        return convertToDTO(updated);
    }

    // Convert entity → DTO
    private OrderResponseDTO convertToDTO(Orders order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getOrderId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus() != null ? order.getStatus() : OrderStatus.PENDING);
        dto.setShippingAddress(order.getShippingAddress());
        dto.setCreatedAt(LocalDateTime.now());

        if (order.getUsers() != null) {
            dto.setUserId(order.getUsers().getUserId());
            dto.setUserName(order.getUsers().getUserName());
        }

        if (order.getListOrderItems() != null) {
            dto.setListItems(
                    order.getListOrderItems().stream().map(item -> {
                        OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                        itemDTO.setOrderItemId(item.getOrderItemId());
                        itemDTO.setQuantity(item.getQuantity());
                        itemDTO.setPrice(item.getPrice());
                        itemDTO.setOrderId(item.getOrders().getOrderId());

                        if (item.getBooks() != null) {
                            itemDTO.setBookId(item.getBooks().getBookId());
                            itemDTO.setBookTitle(item.getBooks().getTitle());
                        }
                        return itemDTO;
                    }).toList()
            );
        }
        return dto;
    }


    public List<OrderResponseDTO> searchOrders(String keyword,
                                               OrderStatus status,
                                               LocalDateTime dateFrom,
                                               LocalDateTime dateTo) {
        return orderRepository.searchOrders(keyword, status, dateFrom, dateTo)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
}
