package com.example.ecommercebooksales.dto.requestDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsRequestDTO {
    @NotNull(message = "UserId không được để trống")
    private Long userId;

    @NotNull(message = "BookId không được để trống")
    private Long bookId;

    @NotNull(message = "Số lượng không được để trống")
    @Positive( message = "Số lượng phải lớn hơn 0")
    private Integer quantity;

    @NotNull(message = "Status không được để trống")
    @Positive(message = "Status phải là 0 (cart) hoặc 1 (order)")
    private Integer status;

    private Long orderId; // chỉ để tham chiếu nếu status = 1, BE không kiểm tra null nữa
}
