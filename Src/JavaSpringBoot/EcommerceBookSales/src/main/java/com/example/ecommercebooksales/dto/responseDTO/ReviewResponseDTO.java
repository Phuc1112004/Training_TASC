package com.example.ecommercebooksales.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponseDTO {
    private Long reviewId;
    private Long userId;
    private Long bookId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
