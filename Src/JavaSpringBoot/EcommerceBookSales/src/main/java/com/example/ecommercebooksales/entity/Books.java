package com.example.ecommercebooksales.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;
    private String title;
    private Long import_price;
    private Long market_price;
    private Long sale_price;
    private Integer stock_quantity;
    private String description;
    private String imageUrl;
    private LocalDateTime created_at;

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

    @OneToMany(mappedBy = "books")
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "books")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "books")
    private List<Review> reviews;
}
