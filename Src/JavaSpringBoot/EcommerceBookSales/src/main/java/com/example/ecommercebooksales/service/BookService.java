package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.BookRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.BookResponseDTO;
import com.example.ecommercebooksales.entity.*;
import com.example.ecommercebooksales.repository.*;
import com.example.ecommercebooksales.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    private BookJdbcRepository bookJdbcRepository;


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
        book.setBookNewId(request.getBookId());

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

        bookRepository.save(book);
        book.setBookNewId(book.getBookId());
        if(request.getBookId() != null){
            List<Books> booksList = bookRepository.findByBookNewId(request.getBookId());
            for (Books item : booksList) {
                item.setBookNewId(book.getBookId());
            }
            bookRepository.saveAll(booksList);
        }
        bookRepository.save(book);

        return convertToDTO(book);
    }

    // ---------------- READ ----------------
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // chi tiết sách có kèm theo gọi ý những sách liên quan có chung tác giả
    public BookResponseDTO getBookById(Long id) {
        BookResponseDTO responseDTO = convertToDTO(bookRepository.findById(id).get());
        if (responseDTO.getAuthorId()!= null){
            Optional<Author> author = authorRepository.findById(responseDTO.getAuthorId());
            if(author.isPresent()){
                List<BookResponseDTO> listAuthor = bookRepository.findByAuthor(author.get()).stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
                responseDTO.setBookResponseDTOList(listAuthor);
            }
        }
        return responseDTO;
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
        List<Books> books = bookRepository.findByBookNewIdOrBookId(id, id);
        List<Items> items = orderItemRepository.findAllByBooksIn(books);
        if (items.isEmpty()) {
            bookRepository.deleteById(id);
            return true;
        }else
            return false;
    }

    // ---------------- CONVERT ----------------
    private BookResponseDTO convertToDTO(Books book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setBookId(book.getBookId());
        dto.setTitle(book.getTitle());

        // Kiểm tra null trước khi lấy tên
        dto.setAuthorName(book.getAuthor() != null ? book.getAuthor().getAuthorName() : null);
        dto.setAuthorId(book.getAuthor() != null ? book.getAuthor().getAuthorId() : null);
        dto.setPublisherName(book.getPublisher() != null ? book.getPublisher().getPublisherName() : null);
        dto.setCategoryName(book.getCategory() != null ? book.getCategory().getCategoryName() : null);

        dto.setImportPrice(book.getImportPrice());
        dto.setMarketPrice(book.getMarketPrice());
        dto.setSalePrice(book.getSalePrice());
        dto.setStockQuantity(book.getStockQuantity());
        dto.setDescription(book.getDescription());
        dto.setImageUrl(book.getImageUrl());
        dto.setCreatedAt(book.getCreatedAt());
        dto.setBookNewId(book.getBookNewId());

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
