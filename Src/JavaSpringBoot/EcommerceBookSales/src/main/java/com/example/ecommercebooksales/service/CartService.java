package com.example.ecommercebooksales.service;


import com.example.ecommercebooksales.dto.requestDTO.CartRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.CartItemResponseDTO;
import com.example.ecommercebooksales.dto.responseDTO.CartResponseDTO;
import com.example.ecommercebooksales.entity.*;
import com.example.ecommercebooksales.enums.OrderStatus;
import com.example.ecommercebooksales.exception.ResourceNotFoundException;
import com.example.ecommercebooksales.repository.CartItemRepository;
import com.example.ecommercebooksales.repository.CartRepository;
import com.example.ecommercebooksales.repository.OrderRepository;
import com.example.ecommercebooksales.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final OrderRepository orderRepository;
    private final UserRepository usersRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;


    public CartResponseDTO createCart(CartRequestDTO request) {
        Users user = usersRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);
        Cart saved = cartRepository.save(cart);

        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(saved.getCartId());
        dto.setUserId(saved.getUser().getUserId());
        return dto;
    }

    public CartResponseDTO getCartByUser(Long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(cart.getCartId());
        dto.setUserId(cart.getUser().getUserId());
        // Map CartItem -> CartItemResponseDTO
        List<CartItemResponseDTO> itemDTOs = cart.getListCartItems().stream()
                .map(item -> {
                    CartItemResponseDTO itemDTO = new CartItemResponseDTO();
                    itemDTO.setCartItemId(item.getCartItemId());
                    itemDTO.setBookId(item.getBooks().getBookId());
                    itemDTO.setBookTitle(item.getBooks().getTitle());
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setPrice(item.getBooks().getSalePrice());
                    return itemDTO;
                })
                .collect(Collectors.toList());

        dto.setListCartItems(itemDTOs);
        return dto;
    }


    @Transactional
    public Orders checkout(Long userId, String shippingAddress) {
        // 1. Lấy giỏ hàng của user
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Giỏ hàng trống"));

        if (cart.getListCartItems().isEmpty()) {
            throw new RuntimeException("Giỏ hàng rỗng, không thể thanh toán");
        }

        // 2. Tạo Orders
        Orders order = new Orders();
        order.setUsers(cart.getUser());
        order.setShippingAddress(shippingAddress);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        long totalAmount = 0L;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getListCartItems()) {
            OrderItem oi = new OrderItem();
            oi.setOrders(order);
            oi.setBooks(cartItem.getBooks());
            oi.setQuantity(cartItem.getQuantity());
            oi.setPrice(cartItem.getBooks().getSalePrice());

            totalAmount += cartItem.getBooks().getSalePrice() * cartItem.getQuantity();
            orderItems.add(oi);
        }

        order.setTotalAmount(totalAmount);
        order.setListOrderItems(orderItems);

        // 3. Lưu Orders + OrderItem
        Orders savedOrder = orderRepository.save(order);

        // 4. Clear giỏ hàng
        cartItemRepository.deleteAll(cart.getListCartItems());

        return savedOrder;
    }


}

