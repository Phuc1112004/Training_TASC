package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
