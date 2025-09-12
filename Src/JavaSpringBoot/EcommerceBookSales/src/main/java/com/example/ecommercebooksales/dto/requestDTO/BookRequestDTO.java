package com.example.ecommercebooksales.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookRequestDTO {
    @NotBlank(message = "Tiêu đề sách không được để trống")
    private String title;
    private Long authorId;
    private Long publisherId;
    private Long categoryId;
    @PositiveOrZero(message = "Giá nhập phải >= 0")
    private Long importPrice;
    @PositiveOrZero(message = "Giá thị trường phải >= 0")
    private Long marketPrice;
    @PositiveOrZero(message = "Giá bán phải >= 0")
    private Long salePrice;
    @PositiveOrZero(message = "Số lượng kho phải >= 0")
    private Integer stockQuantity;
    private String description;
    private String imageUrl;
}
