//package com.example.ecommercebooksales.controller;
//
//import com.example.ecommercebooksales.dto.requestDTO.CartItemRequestDTO;
//import com.example.ecommercebooksales.dto.responseDTO.CartItemResponseDTO;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/cart-items")
//public class CartItemController {
//    private final CartItemService cartItemService;
//
//    public CartItemController(CartItemService cartItemService) {
//        this.cartItemService = cartItemService;
//    }
//
//    @PostMapping
//    public ResponseEntity<CartItemResponseDTO> addCartItem(@RequestBody @Valid CartItemRequestDTO request) {
//        return ResponseEntity.ok(cartItemService.addCartItem(request));
//    }
//
//    @GetMapping("/cart/{cartId}")
//    public ResponseEntity<List<CartItemResponseDTO>> getItemsByCart(@PathVariable Long cartId) {
//        return ResponseEntity.ok(cartItemService.getItemsByCart(cartId));
//    }
//
//    @DeleteMapping("/{cartItemId}")
//    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId) {
//        cartItemService.removeCartItem(cartItemId);
//        return ResponseEntity.noContent().build();
//    }
//}
//
