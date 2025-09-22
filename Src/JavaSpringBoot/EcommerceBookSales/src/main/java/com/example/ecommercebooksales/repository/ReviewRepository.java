package com.example.ecommercebooksales.repository;


import com.example.ecommercebooksales.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Tìm review của 1 user cho 1 sách (để check duplicate)
    Optional<Review> findByUsersUserIdAndBooksBookId(Long userId, Long bookId);

    // Lấy danh sách review theo sách
    List<Review> findByBooksBookId(Long bookId);

    // Lấy danh sách review theo user
    List<Review> findByUsersUserId(Long userId);
}
