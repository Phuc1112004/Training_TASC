package com.example.ecommercebooksales.controller;


import com.example.ecommercebooksales.dto.CategoryDTO;
import com.example.ecommercebooksales.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategories(@RequestBody CategoryDTO request) {
        CategoryDTO createCategory = categoryService.createCategory(request);
        return ResponseEntity.ok(createCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        if (category == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(category);
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategories(@PathVariable Long id,
                                                   @RequestBody CategoryDTO request) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, request);
        if (updatedCategory == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedCategory);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategories(@PathVariable Long id) {
        boolean deleted = categoryService.deleteCategory(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build(); // 204
    }
}
