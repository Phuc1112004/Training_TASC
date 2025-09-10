package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.AuthorDTO;
import com.example.ecommercebooksales.entity.Author;
import com.example.ecommercebooksales.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // ---------------- CREATE ----------------
    public AuthorDTO createAuthor(AuthorDTO request) {
        Author author = new Author();
        author.setAuthor_name(request.getAuthorName());
        author.setBiography(request.getBiography());

        Author saved = authorRepository.save(author);
        return convertToDTO(saved);
    }


    // ---------------- READ ----------------
    public List<AuthorDTO> getAllAuthor() {
        return authorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------------- FIND BY ID ----------------
    public AuthorDTO getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // ---------------- UPDATE ----------------
    public AuthorDTO updateAuthor(Long id, AuthorDTO request) {
        return authorRepository.findById(id)
                .map(author ->{
                    author.setAuthor_name(request.getAuthorName());
                    author.setBiography(request.getBiography());

                    Author updated = authorRepository.save(author);
                    return convertToDTO(updated);
                }).orElse(null);
    }



    // ---------------- DELETE ----------------
    public boolean deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) return false;
        authorRepository.deleteById(id);
        return true;
    }


    // ---------------- CONVERT ----------------  những dữ liệu sinh ra khi create
    private AuthorDTO convertToDTO(Author author){
        AuthorDTO dto = new AuthorDTO();
        dto.setAuthorId(author.getAuthor_id());
        dto.setAuthorName(author.getAuthor_name());
        dto.setBiography(author.getBiography());
        return dto;
    }
}
