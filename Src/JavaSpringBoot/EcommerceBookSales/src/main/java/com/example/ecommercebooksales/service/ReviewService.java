package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.ReviewRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.ReviewResponseDTO;
import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.Review;
import com.example.ecommercebooksales.entity.Users;
import com.example.ecommercebooksales.repository.BookRepository;
import com.example.ecommercebooksales.repository.ReviewRepository;
import com.example.ecommercebooksales.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public ReviewResponseDTO addReview(ReviewRequestDTO request) {
        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Books book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Review review = new Review();
        review.setUsers(user);
        review.setBooks(book);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());

        Review saved = reviewRepository.save(review);

        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setReviewId(saved.getReviewId());
        dto.setUserId(saved.getUsers().getUserId());
        dto.setBookId(saved.getBooks().getBookId());
        dto.setRating(saved.getRating());
        dto.setComment(saved.getComment());
        dto.setCreatedAt(saved.getCreatedAt());
        return dto;
    }

    public List<ReviewResponseDTO> getReviewsByBook(Long bookId) {
        return reviewRepository.findByBooks_BookId(bookId).stream().map(r -> {
            ReviewResponseDTO dto = new ReviewResponseDTO();
            dto.setReviewId(r.getReviewId());
            dto.setUserId(r.getUsers().getUserId());
            dto.setBookId(r.getBooks().getBookId());
            dto.setRating(r.getRating());
            dto.setComment(r.getComment());
            dto.setCreatedAt(r.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());
    }
}

