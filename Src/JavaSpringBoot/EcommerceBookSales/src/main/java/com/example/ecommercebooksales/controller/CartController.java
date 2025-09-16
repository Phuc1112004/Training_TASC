package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.dto.requestDTO.CartRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.CartResponseDTO;
import com.example.ecommercebooksales.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartResponseDTO> createCart(@RequestBody CartRequestDTO request) {
        return ResponseEntity.ok(cartService.createCart(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponseDTO> getCartByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }
}
