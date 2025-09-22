package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.ReviewRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.ReviewResponseDTO;
import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.Review;
import com.example.ecommercebooksales.entity.Users;
import com.example.ecommercebooksales.repository.BookRepository;
import com.example.ecommercebooksales.repository.ReviewRepository;
import com.example.ecommercebooksales.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private ReviewResponseDTO toDTO(Review r) {
        return new ReviewResponseDTO(
                r.getReviewId(),
                r.getUsers().getUserName(),
                r.getBooks().getTitle(),
                r.getRating(),
                r.getComment(),
                r.getCreatedAt(),
                r.getUpdatedAt()
        );
    }

    public ReviewResponseDTO addReview(ReviewRequestDTO request) {
        reviewRepository.findByUsersUserIdAndBooksBookId(request.getUserId(), request.getBookId())
                .ifPresent(r -> { throw new RuntimeException("User đã review sách này rồi!"); });

        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Books book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book không tồn tại"));

        Review review = new Review();
        review.setUsers(user);
        review.setBooks(book);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        return toDTO(reviewRepository.save(review));
    }

    public List<ReviewResponseDTO> getReviewsByBook(Long bookId) {
        return reviewRepository.findByBooksBookId(bookId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDTO> getReviewsByUser(Long userId) {
        return reviewRepository.findByUsersUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewResponseDTO updateReview(Long reviewId, ReviewRequestDTO request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setUpdatedAt(LocalDateTime.now());

        return toDTO(reviewRepository.save(review));
    }
}

