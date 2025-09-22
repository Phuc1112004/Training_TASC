package com.example.ecommercebooksales.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class ReviewResponseDTO {
    private Long reviewId;
    private String userName;
    private String bookTitle;
    private int rating;
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
