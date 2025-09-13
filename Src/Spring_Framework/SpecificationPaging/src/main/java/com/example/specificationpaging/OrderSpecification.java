package com.example.specificationpaging;

import com.example.specificationpaging.entities.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

    public static Specification<Order> hasCustomerName(String customerName) {
        return (root, query, cb) -> {
            if (customerName == null) return null;
            return cb.like(root.get("customer").get("name"), "%" + customerName + "%");
        };
    }

    public static Specification<Order> hasMinTotal(Long minTotal) {
        return (root, query, cb) -> {
            if (minTotal == null) return null;
            return cb.greaterThanOrEqualTo(root.get("total"), minTotal);
        };
    }
}
