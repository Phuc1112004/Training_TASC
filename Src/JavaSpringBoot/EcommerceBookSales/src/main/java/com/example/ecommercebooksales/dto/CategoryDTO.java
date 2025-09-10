package com.example.ecommercebooksales.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoryDTO {
    private String category_name;
    private String description;
}
