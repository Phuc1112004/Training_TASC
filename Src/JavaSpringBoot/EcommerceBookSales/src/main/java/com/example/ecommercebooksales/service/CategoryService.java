package com.example.ecommercebooksales.service;


import com.example.ecommercebooksales.dto.CategoryDTO;
import com.example.ecommercebooksales.entity.Category;
import com.example.ecommercebooksales.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ---------------- CREATE ----------------
    public CategoryDTO createCategory(CategoryDTO request) {
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent not found"));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        Category saved = categoryRepository.save(category);
        return convertToDTO(saved);
    }

    // ---------------- READ ----------------
    public List<CategoryDTO> getAllCategory() {
        return categoryRepository.findByParentIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // ---------------- FIND BY ID ----------------
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // ---------------- UPDATE ----------------
    public CategoryDTO updateCategory(Long id, CategoryDTO request) {
        return categoryRepository.findById(id)
                .map(category ->{
                    category.setCategoryName(request.getCategoryName());
                    category.setDescription(request.getDescription());

                    if (request.getParentId() != null) {
                        Category parent = categoryRepository.findById(request.getParentId())
                                .orElseThrow(() -> new RuntimeException("Parent not found"));
                        category.setParent(parent);
                    } else {
                        category.setParent(null);
                    }

                    Category updated = categoryRepository.save(category);
                    return convertToDTO(updated);
                }).orElse(null);
    }

    // ---------------- DELETE ----------------
    public boolean deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) return false;
        categoryRepository.deleteById(id);
        return true;
    }

    // ---------------- CONVERT ----------------
    private CategoryDTO convertToDTO(Category category){
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());

        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getCategoryId());
            dto.setParentName(category.getParent().getCategoryName());
        }

        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            List<CategoryDTO> childDTOs = category.getChildren()
                    .stream()
                    .map(this::convertToDTO) // đệ quy
                    .toList();
            dto.setChildren(childDTOs);
        }
        return dto;
    }
}
