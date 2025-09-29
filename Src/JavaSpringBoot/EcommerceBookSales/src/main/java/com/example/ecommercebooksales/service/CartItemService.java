package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.CartItemRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.CartItemResponseDTO;
import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.Cart;
import com.example.ecommercebooksales.entity.CartItem;
import com.example.ecommercebooksales.exception.ResourceNotFoundException;
import com.example.ecommercebooksales.repository.BookRepository;
import com.example.ecommercebooksales.repository.CartItemRepository;
import com.example.ecommercebooksales.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    public CartItemService(CartItemRepository cartItemRepository,
                           CartRepository cartRepository,
                           BookRepository booksRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.bookRepository = booksRepository;
    }

    public CartItemResponseDTO addCartItem(CartItemRequestDTO request) {
        Cart cart = cartRepository.findByUser_UserId(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + request.getUserId()));

        Books book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + request.getBookId()));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setBooks(book);
        cartItem.setQuantity(request.getQuantity());

        CartItem saved = cartItemRepository.save(cartItem);

        return mapToDTO(saved);
    }

    public List<CartItemResponseDTO> getItemsByCart(Long cartId) {
        List<CartItem> items = cartItemRepository.findByCart_CartId(cartId);
        if (items.isEmpty()) {
            throw new ResourceNotFoundException("No items found for cart id: " + cartId);
        }
        return items.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public void removeCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new RuntimeException("CartItem not found");
        }
        cartItemRepository.deleteById(cartItemId);
    }

    private CartItemResponseDTO mapToDTO(CartItem item) {
        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setCartItemId(item.getCartItemId());
        dto.setBookId(item.getBooks().getBookId());
        dto.setBookTitle(item.getBooks().getTitle());
        dto.setPrice(item.getBooks().getSalePrice());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}

