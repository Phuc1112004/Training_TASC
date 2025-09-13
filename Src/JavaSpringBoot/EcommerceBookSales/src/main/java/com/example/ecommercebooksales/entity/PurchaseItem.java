package com.example.ecommercebooksales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.print.Book;

@Entity
@Data
@Table(name = "purchase_item")
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseItemId;
    private int quantity;
    private Long unitPrice;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books books;

}
