package com.example.ecommercebooksales.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne
    private Users users;

    @ManyToOne
    private Books books;

    private Integer quantity;

    private Long price;
    private Integer status;

    @ManyToOne
    private Orders orders; // null -> giỏ hàng, != null -> đã order
}
