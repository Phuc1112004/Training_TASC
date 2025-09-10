package com.example.ecommercebooksales.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publisher_id;

    private String publisher_name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private String country;
    private LocalDate founded_year;
    private String description;
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "publisher")
    private List<Books> books;
}
