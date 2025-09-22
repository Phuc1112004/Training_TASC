package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.requestDTO.ItemsRequestDTO;
import com.example.ecommercebooksales.dto.responseDTO.ItemsResponseDTO;
import com.example.ecommercebooksales.entity.Books;
import com.example.ecommercebooksales.entity.Items;
import com.example.ecommercebooksales.entity.Orders;
import com.example.ecommercebooksales.entity.Users;
import com.example.ecommercebooksales.exception.ResourceNotFoundException;
import com.example.ecommercebooksales.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    // ----------------- Thêm Item (cart hoặc order) -----------------
    public ItemsResponseDTO addItem(ItemsRequestDTO request) {
        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User không tồn tại"));
        Books book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book không tồn tại"));

        Items item = new Items();
        item.setUsers(user);
        item.setBooks(book);
        item.setQuantity(request.getQuantity());
        item.setPrice(book.getSalePrice());
        item.setStatus(request.getStatus());

        // Nếu status = 1, lấy orderId tham chiếu, BE không kiểm tra null
        if (request.getStatus() == 1 && request.getOrderId() != null) {
            Orders order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order không tồn tại"));
            item.setOrders(order);
        } else {
            item.setOrders(null); // cart
        }

        Items saved = itemRepository.save(item);
        return mapToDTO(saved);
    }

    public List<ItemsResponseDTO> getAllItems() {
        List<Items> items = itemRepository.findAll();
        return items.stream().map(item -> mapToDTO(item)).collect(Collectors.toList());
    }


    // ----------------- Lấy Items theo user hoặc order -----------------
    public List<ItemsResponseDTO> getCartItems(Long userId) {
        return itemRepository.findByUsers_UserIdAndStatus(userId, 0)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ItemsResponseDTO> getOrderItems(Long orderId) {
        return itemRepository.findByOrders_OrderIdAndStatus(orderId, 1)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteItem(Long itemId, boolean isAdmin) {
        Items item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item không tồn tại"));

        if (!isAdmin && item.getStatus() != 0) {
            throw new RuntimeException("Khách hàng chỉ có thể xóa sản phẩm trong giỏ hàng");
        }

        itemRepository.delete(item);
    }



    // ----------------- Map entity → DTO -----------------
    private ItemsResponseDTO mapToDTO(Items item) {
        ItemsResponseDTO dto = new ItemsResponseDTO();
        dto.setItemId(item.getItemId());
        dto.setBookId(item.getBooks().getBookId());
        dto.setBookTitle(item.getBooks().getTitle());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setStatus(item.getStatus());
        dto.setOrderId(item.getOrders() != null ? item.getOrders().getOrderId() : null);
        return dto;
    }

    //    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private BookRepository bookRepository;


//    public List<OrderItemResponseDTO> getItemsByOrderId(Long orderId) {
//        List<Items> items = orderItemRepository.findByOrders_OrderId(orderId);
//        return items.stream()
//                .map(this::convertToDTO)
//                .toList();
//    }

//
//    public void deleteOrderItem(Long orderItemId) {
//        if (!orderItemRepository.existsById(orderItemId)) {
//            throw new RuntimeException("Order item not found");
//        }
//        orderItemRepository.deleteById(orderItemId);
//    }
//
//    public OrderItemResponseDTO updateOrderItemQuantity(Long orderItemId, int quantity) {
//        OrderItem item = orderItemRepository.findById(orderItemId)
//                .orElseThrow(() -> new RuntimeException("Order item not found"));
//        item.setQuantity(quantity);
//        OrderItem updated = orderItemRepository.save(item);
//        return convertToDTO(updated);
//    }




//    private ItemsResponseDTO convertToDTO(Items items) {
//        ItemsResponseDTO dto = new ItemsResponseDTO();
//        dto.setItemId(items.getItemId());
//        dto.setQuantity(items.getQuantity());
//        dto.setPrice(items.getPrice());
//
//        return dto;
//    }
}
