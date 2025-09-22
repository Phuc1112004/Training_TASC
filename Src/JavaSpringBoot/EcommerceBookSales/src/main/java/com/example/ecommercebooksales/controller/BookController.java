package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.dto.requestDTO.BookRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.BookResponseDTO;
import com.example.ecommercebooksales.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ---------------- CREATE ----------------
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookRequestDTO request) {
        BookResponseDTO createdBook = bookService.createBook(request);
        return ResponseEntity.ok(createdBook);
    }

    // ---------------- READ ----------------
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());

    }

//    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
//    public ResponseEntity<List<BookResponseDTO>> getAllBooksJdbc() {
//        try {
//            List<BookResponseDTO> books = bookService.getAllBooksJdbc();
//            return ResponseEntity.ok(books);
//        } catch (RuntimeException e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(null);
//        }
//    }



    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        BookResponseDTO book = bookService.getBookById(id);
        if (book == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(book);
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id,
                                                      @RequestBody @Valid BookRequestDTO request) {
        BookResponseDTO updatedBook = bookService.createBook(request);
        if (updatedBook == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedBook);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build(); // 204
    }


    // search dùng specification
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<List<BookResponseDTO>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice
    ) {
        List<BookResponseDTO> books = bookService.searchBooks(title, authorName, categoryName, minPrice, maxPrice);
        return ResponseEntity.ok(books);
    }
}

