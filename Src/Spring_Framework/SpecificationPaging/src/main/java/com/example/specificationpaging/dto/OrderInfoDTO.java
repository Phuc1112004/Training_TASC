package com.example.specificationpaging.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInfoDTO {
    private Long orderId;
    private String customerName;
    private String bookTitle;
    private Long total;

    public OrderInfoDTO(Long orderId, String customerName, String bookTitle, Long total) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.bookTitle = bookTitle;
        this.total = total;
    }

}

