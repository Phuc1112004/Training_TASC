package com.example.specificationpaging;

import com.example.specificationpaging.dto.OrderInfoDTO;
import com.example.specificationpaging.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Lấy danh sách Order theo filter động và paging
     */
    public Page<Order> getOrders(String customerName, Long minTotal, int page, int size) {

        // Tạo từng Specification
        Specification<Order> customerSpec = OrderSpecification.hasCustomerName(customerName);
        Specification<Order> totalSpec = OrderSpecification.hasMinTotal(minTotal);

        // Kết hợp Specification động, bỏ qua null
        Specification<Order> spec = combineSpecs(customerSpec, totalSpec);

        // Trả về kết quả với paging
        return orderRepository.findAll(spec, PageRequest.of(page, size));
    }

    /**
     * Lấy danh sách OrderInfoDTO (JOIN nhiều bảng, chỉ lấy 1-2 trường)
     */
    public List<OrderInfoDTO> getOrderInfo(String customerName, Long minTotal) {
        return orderRepository.findOrderInfoWithJoin(customerName, minTotal);
    }

    /**
     * Helper method kết hợp nhiều Specification, bỏ qua null
     */
    @SafeVarargs
    private final <T> Specification<T> combineSpecs(Specification<T>... specs) {
        Specification<T> result = null;
        for (Specification<T> s : specs) {
            if (s != null) {
                result = (result == null) ? s : result.and(s);
            }
        }
        return result;
    }
}
