package com.example.ecommercebooksales.service;


import com.example.ecommercebooksales.dto.requestDTO.CartRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.CartItemResponseDTO;
import com.example.ecommercebooksales.dto.responseDTO.CartResponseDTO;
import com.example.ecommercebooksales.entity.Cart;
import com.example.ecommercebooksales.entity.Users;
import com.example.ecommercebooksales.repository.CartRepository;
import com.example.ecommercebooksales.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository usersRepository;

    public CartService(CartRepository cartRepository, UserRepository usersRepository) {
        this.cartRepository = cartRepository;
        this.usersRepository = usersRepository;
    }

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
        List<CartItemResponseDTO> itemDTOs = cart.getCartItems().stream()
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

}

