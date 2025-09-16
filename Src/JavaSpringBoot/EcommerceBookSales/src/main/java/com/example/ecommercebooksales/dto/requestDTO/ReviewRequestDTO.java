package com.example.ecommercebooksales.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {
    private Long userId;
    private Long bookId;
    private int rating;
    private String comment;
}
