package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookRequestDTO {
    private String title;
    private Long authorId;
    private Long publisherId;
    private Long categoryId;
    private Long importPrice;
    private Long marketPrice;
    private Long salePrice;
    private Integer stockQuantity;
    private String description;
    private String imageUrl;
}
