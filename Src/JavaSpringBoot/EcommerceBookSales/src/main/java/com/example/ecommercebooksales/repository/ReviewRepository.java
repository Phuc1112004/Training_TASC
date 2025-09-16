package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Tìm tất cả review theo bookId
    List<Review> findByBooks_BookId(Long bookId);

    // Tìm tất cả review theo userId
    List<Review> findByUsers_UserId(Long userId);
}
