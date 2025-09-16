package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.dto.requestDTO.ReviewRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.ReviewResponseDTO;
import com.example.ecommercebooksales.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> addReview(@RequestBody ReviewRequestDTO request) {
        return ResponseEntity.ok(reviewService.addReview(request));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.getReviewsByBook(bookId));
    }
}

