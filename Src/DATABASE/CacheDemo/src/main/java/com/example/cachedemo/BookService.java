package com.example.cachedemo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Lấy từ cache nếu có, nếu chưa thì query DB rồi lưu vào cache
    @Cacheable(value = "books", key = "#id")
    public Optional<Book> getBookById(Long id) {
        System.out.println("Fetching from DB... id=" + id);
        return bookRepository.findById(id);
    }

    // Tạo mới sách
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Update sách + update cache
    @CachePut(value = "books", key = "#book.id")
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    // Xoá sách + xoá cache
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
