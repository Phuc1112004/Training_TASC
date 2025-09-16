package com.example.ecommercebooksales.dto.requestDTO;

import com.example.ecommercebooksales.entity.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class CartRequestDTO {
    private Long cartId;
    private Long userId;
    private List<CartItemRequestDTO> listCartItems;
}
