package com.example.ecommercebooksales.repository;

import com.example.ecommercebooksales.entity.Author;
import com.example.ecommercebooksales.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookRepository extends JpaRepository<Books, Long>, JpaSpecificationExecutor {
    List<Books> findByTitleContaining(String title_keyword);
    List<Books> findByBookNewId(Long title_keyword);
    List<Books> findByBookNewIdOrBookId(Long title_keyword, Long id);
    List<Books> findByAuthor(Author author);

}
