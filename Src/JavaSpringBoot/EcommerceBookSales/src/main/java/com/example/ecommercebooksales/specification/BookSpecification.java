package com.example.ecommercebooksales.specification;


import com.example.ecommercebooksales.entity.Books;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Books> hasTitle(String title) {
        return (root, query, cb) ->
                title == null ? null : cb.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Books> hasAuthor(Long authorId) {
        return (root, query, cb) ->
                authorId == null ? null : cb.equal(root.get("author").get("authorId"), authorId);
    }

    public static Specification<Books> hasCategory(Long categoryId) {
        return (root, query, cb) ->
                categoryId == null ? null : cb.equal(root.get("category").get("categoryId"), categoryId);
    }

    public static Specification<Books> priceBetween(Long minPrice, Long maxPrice) {
        return (root, query, cb) -> {
            if (minPrice != null && maxPrice != null) {
                return cb.between(root.get("salePrice"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return cb.greaterThanOrEqualTo(root.get("salePrice"), minPrice);
            } else if (maxPrice != null) {
                return cb.lessThanOrEqualTo(root.get("salePrice"), maxPrice);
            }
            return null;
        };
    }
}

