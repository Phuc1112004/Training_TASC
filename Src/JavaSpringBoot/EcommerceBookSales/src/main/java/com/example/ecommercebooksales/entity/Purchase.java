package com.example.ecommercebooksales.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchase_id;

    private String supplier_name;
    private Long total_cost;
    private LocalDate purchase_date;

    @OneToMany(mappedBy = "purchase")
    private List<PurchaseItem> items;
}
