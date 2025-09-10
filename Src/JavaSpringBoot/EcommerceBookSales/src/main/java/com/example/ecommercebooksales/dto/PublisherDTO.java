package com.example.ecommercebooksales.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class PublisherDTO {
    private String publisher_name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private String country;
    private LocalDate founded_year;
    private String description;
}
