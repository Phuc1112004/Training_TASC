package com.example.ecommercebooksales.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String title;
    private Long importPrice;
    private Long marketPrice;
    private Long salePrice;
    private Integer stockQuantity;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;
    private Long bookNewId;     //  sách mới nhất của bản chỉnh sửa

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "books")
    private List<PurchaseItem> purchaseItems;

//    @OneToMany(mappedBy = "books")
//    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "books")
    private List<OrderItem> items;

    @OneToMany(mappedBy = "books")
    private List<Review> reviews;

    public Books(Long bookId, String title,
                 double importPrice, double marketPrice, double salePrice,
                 int stockQuantity, String description, String imageUrl, Timestamp createdAt) {
        this.bookId = bookId;
        this.title = title;
        this.importPrice = (long) importPrice;
        this.marketPrice = (long) marketPrice;
        this.salePrice = (long) salePrice;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt.toLocalDateTime();
    }

}
