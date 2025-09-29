//package com.example.ecommercebooksales.controller;
//
//
//
//import com.example.ecommercebooksales.service.OrderItemService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("api/items")
//public class ItemController {
//    private final OrderItemService orderItemService;
//
//    @GetMapping
//    public ResponseEntity<List<ItemsResponseDTO>> getAllItems() {
//        List<ItemsResponseDTO> items = orderItemService.getAllItems();
//        return ResponseEntity.ok(items);
//    }
//
//
//    // ----------------- Thêm item (giỏ hàng hoặc order) -----------------
//    @PostMapping
//    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
//    public ResponseEntity<ItemsResponseDTO> addItem(@RequestBody @Valid ItemsRequestDTO request) {
//        ItemsResponseDTO item = orderItemService.addItem(request);
//        return ResponseEntity.ok(item);
//    }
//
//    // ----------------- Lấy giỏ hàng theo user -----------------
//    @GetMapping("/cart/{userId}")
//    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
//    public ResponseEntity<List<ItemsResponseDTO>> getCartItems(@PathVariable Long userId) {
//        List<ItemsResponseDTO> items = orderItemService.getCartItems(userId);
//        return ResponseEntity.ok(items);
//    }
//
//    // ----------------- Lấy order items theo orderId -----------------
//    @GetMapping("/order/{orderId}")
//    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
//    public ResponseEntity<List<ItemsResponseDTO>> getOrderItems(@PathVariable Long orderId) {
//        List<ItemsResponseDTO> items = orderItemService.getOrderItems(orderId);
//        return ResponseEntity.ok(items);
//    }
//
//    // ----------------- Xóa item -----------------
//    @DeleteMapping("/{itemId}")
//    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
//    public ResponseEntity<Map<String, String>> deleteItems(@PathVariable @Valid Long itemId,
//                                                           @RequestParam(defaultValue = "false") boolean isAdmin) {
//        orderItemService.deleteItem(itemId, isAdmin);
//        return ResponseEntity.ok(Map.of("message", "Xóa thành công item có ID = " + itemId));
//    }
//
////    // ----------------- Xóa item -----------------
////    @DeleteMapping("/{itemId}")
////    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
////        itemService.removeItem(itemId);
////        return ResponseEntity.noContent().build();
////    }
//}
