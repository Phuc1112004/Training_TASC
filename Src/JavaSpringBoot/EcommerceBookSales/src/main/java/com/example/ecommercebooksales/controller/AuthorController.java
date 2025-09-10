package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.dto.AuthorDTO;
import com.example.ecommercebooksales.dto.requestDTO.PurchaseRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.PurchaseResponseDTO;
import com.example.ecommercebooksales.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO request) {
        AuthorDTO createAuthor = authorService.createAuthor(request);
        return ResponseEntity.ok(createAuthor);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthor());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO book = authorService.getAuthorById(id);
        if (book == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(book);
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthors(@PathVariable Long id,
                                                          @RequestBody AuthorDTO request) {
        AuthorDTO updatedBook = authorService.updateAuthor(id, request);
        if (updatedBook == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedBook);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthors(@PathVariable Long id) {
        boolean deleted = authorService.deleteAuthor(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build(); // 204
    }
}
