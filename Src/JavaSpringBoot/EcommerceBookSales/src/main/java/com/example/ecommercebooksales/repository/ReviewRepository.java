package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
