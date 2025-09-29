package com.example.ecommercebooksales.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books books;

    private Integer quantity;

    private Long price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders; // null -> giỏ hàng, != null -> đã order
}
