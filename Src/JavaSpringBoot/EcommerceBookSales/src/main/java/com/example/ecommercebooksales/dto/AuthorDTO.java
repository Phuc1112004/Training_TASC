package com.example.ecommercebooksales.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthorDTO {
    private Long authorId;
    private String authorName;
    private String biography;
}
