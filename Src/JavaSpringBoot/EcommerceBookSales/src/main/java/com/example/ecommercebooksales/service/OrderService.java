package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.OrderRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.OrderItemResponseDTO;
import com.example.ecommercebooksales.dto.responseDTO.OrderResponseDTO;
import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.OrderItem;
import com.example.ecommercebooksales.entity.Orders;
import com.example.ecommercebooksales.entity.Users;
import com.example.ecommercebooksales.repository.BookRepository;
import com.example.ecommercebooksales.repository.OrderRepository;
import com.example.ecommercebooksales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    // ✅ Tạo đơn hàng mới
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        // Lấy user
        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo đơn hàng mới
        Orders order = new Orders();
        order.setUsers(user);
        order.setStatus(request.getStatus());
        order.setShippingAddress(request.getShippingAddress());
        order.setCreatedAt(request.getCreatedAt());
        order.setOrderItems(new ArrayList<>()); // khởi tạo list

        // Tạo order items
        if (request.getListOrderItems() != null) {
            for (var itemReq : request.getListOrderItems()) {
                Books book = bookRepository.findById(itemReq.getBookId())
                        .orElseThrow(() -> new RuntimeException("Book not found"));

                OrderItem item = new OrderItem();
                item.setOrders(order); // gán quan hệ 1 chiều
                item.setBooks(book);
                item.setQuantity(itemReq.getQuantity());
                item.setPrice(book.getSalePrice());

                order.getOrderItems().add(item);
            }
        }

        // Tính tổng tiền
        long total = order.getOrderItems().stream()
                .mapToLong(i -> i.getPrice() * i.getQuantity())
                .sum();
        order.setTotalAmount(total);

        // Lưu đơn hàng (cả orderItems tự động lưu nhờ cascade)
        Orders savedOrder = orderRepository.save(order);

        return convertToDTO(savedOrder);
    }

    // ✅ Lấy đơn hàng theo ID
    public OrderResponseDTO getOrderById(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    // ✅ Lấy tất cả đơn hàng
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ✅ Xóa đơn hàng
    public void deleteOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }

    // ✅ Cập nhật trạng thái đơn hàng
    public OrderResponseDTO updateOrderStatus(Long orderId, String status) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        Orders updated = orderRepository.save(order);
        return convertToDTO(updated);
    }

    // ✅ Convert entity → DTO
    private OrderResponseDTO convertToDTO(Orders order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getOrderId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setCreatedAt(order.getCreatedAt());

        if (order.getUsers() != null) {
            dto.setUserId(order.getUsers().getUserId());
            dto.setUserName(order.getUsers().getUserName());
        }

        if (order.getOrderItems() != null) {
            dto.setListOrderItems(
                    order.getOrderItems().stream().map(item -> {
                        OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                        itemDTO.setOrderItemId(item.getOrderItemId());
                        itemDTO.setQuantity(item.getQuantity());
                        itemDTO.setPrice(item.getPrice());
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
}
