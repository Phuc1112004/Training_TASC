package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.dto.requestDTO.ReviewRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.ReviewResponseDTO;
import com.example.ecommercebooksales.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ReviewResponseDTO addReview(@RequestBody @Valid ReviewRequestDTO request) {
        return reviewService.addReview(request);
    }

    @GetMapping("/book/{bookId}")
    public List<ReviewResponseDTO> getReviewsByBook(@PathVariable Long bookId) {
        return reviewService.getReviewsByBook(bookId);
    }

    @GetMapping("/user/{userId}")
    public List<ReviewResponseDTO> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviewsByUser(userId);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequestDTO request
    ) {
        ReviewResponseDTO response = reviewService.updateReview(reviewId, request);
        return ResponseEntity.ok(response);
    }
}

