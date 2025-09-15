package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.BookRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.BookResponseDTO;
import com.example.ecommercebooksales.entity.Author;
import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.Category;
import com.example.ecommercebooksales.entity.Publisher;
import com.example.ecommercebooksales.repository.*;
import com.example.ecommercebooksales.specification.BookSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private BookJdbcRepository bookJdbcRepository;


    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       PublisherRepository publisherRepository,
                       CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
    }
    // ---------------- CREATE ----------------
    public BookResponseDTO createBook(BookRequestDTO request) {
        Books book = new Books();
        book.setTitle(request.getTitle());
        book.setImportPrice(request.getImportPrice());
        book.setMarketPrice(request.getMarketPrice());
        book.setSalePrice(request.getSalePrice());
        book.setStockQuantity(request.getStockQuantity());
        book.setDescription(request.getDescription());
        book.setImageUrl(request.getImageUrl());
        book.setCreatedAt(LocalDateTime.now());

        // set author, publisher, category
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Publisher publisher = publisherRepository.findById(request.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);

        Books saved = bookRepository.save(book);
        return convertToDTO(saved);
    }

    // ---------------- READ ----------------
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookResponseDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // ---------------- UPDATE ----------------
    public BookResponseDTO updateBook(Long id, BookRequestDTO request) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(request.getTitle());
                    book.setImportPrice(request.getImportPrice());
                    book.setMarketPrice(request.getMarketPrice());
                    book.setSalePrice(request.getSalePrice());
                    book.setStockQuantity(request.getStockQuantity());
                    book.setDescription(request.getDescription());
                    book.setImageUrl(request.getImageUrl());

                    Author author = authorRepository.findById(request.getAuthorId())
                            .orElseThrow(() -> new RuntimeException("Author not found"));
                    Publisher publisher = publisherRepository.findById(request.getPublisherId())
                            .orElseThrow(() -> new RuntimeException("Publisher not found"));
                    Category category = categoryRepository.findById(request.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"));

                    book.setAuthor(author);
                    book.setPublisher(publisher);
                    book.setCategory(category);

                    Books updated = bookRepository.save(book);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }

    // ---------------- DELETE ----------------
    public boolean deleteBook(Long id) {
        if (!bookRepository.existsById(id)) return false;
        bookRepository.deleteById(id);
        return true;
    }

    // ---------------- CONVERT ----------------
    private BookResponseDTO convertToDTO(Books book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setBookId(book.getBookId());
        dto.setTitle(book.getTitle());

        // Kiểm tra null trước khi lấy tên
        dto.setAuthorName(book.getAuthor() != null ? book.getAuthor().getAuthorName() : null);
        dto.setPublisherName(book.getPublisher() != null ? book.getPublisher().getPublisherName() : null);
        dto.setCategoryName(book.getCategory() != null ? book.getCategory().getCategoryName() : null);

        dto.setImportPrice(book.getImportPrice());
        dto.setMarketPrice(book.getMarketPrice());
        dto.setSalePrice(book.getSalePrice());
        dto.setStockQuantity(book.getStockQuantity());
        dto.setDescription(book.getDescription());
        dto.setImageUrl(book.getImageUrl());
        dto.setCreatedAt(book.getCreatedAt());

        return dto;
    }

    // Lấy tất cả sách (JdbcTemplate)
    public List<BookResponseDTO> getAllBooksJdbc() {
        return bookJdbcRepository.getAllBoosJdbc();
    }

    // Dùng Specification để tìm kiếm Book
    public List<BookResponseDTO> searchBooks(String title, Long authorId, Long categoryId, Long minPrice, Long maxPrice) {
        Specification<Books> spec = BookSpecification.hasTitle(title)
                .and(BookSpecification.hasAuthor(authorId))
                .and(BookSpecification.hasCategory(categoryId))
                .and(BookSpecification.priceBetween(minPrice, maxPrice));

        List<Books> books = bookRepository.findAll(spec);

        return books.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
