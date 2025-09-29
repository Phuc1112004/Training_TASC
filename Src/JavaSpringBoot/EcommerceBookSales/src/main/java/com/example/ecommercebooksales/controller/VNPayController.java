package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.entity.Orders;
import com.example.ecommercebooksales.service.OrderService;
import com.example.ecommercebooksales.service.PaymentService;
import com.example.ecommercebooksales.service.VNPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vnpay")
public class VNPayController {

    private final VNPayService vnpayService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    @PostMapping("/create-payment/{orderId}")
    public String createPayment(@PathVariable Long orderId, HttpServletRequest request) throws Exception {

        Orders order = orderService.findById(orderId);

        paymentService.createOrGetVnpayPayment(order);

        String orderInfo = "Thanh toán đơn hàng #" + orderId;
        String ipAddr = request.getRemoteAddr();
        if (ipAddr == null || ipAddr.isEmpty() || ipAddr.equals("0:0:0:0:0:0:0:1")) ipAddr = "127.0.0.1";

        return vnpayService.createPaymentUrl(orderId, order.getTotalAmount(), ipAddr, orderInfo);
    }

    @GetMapping("/return")
    public ResponseEntity<String> vnpayReturn(HttpServletRequest request) throws Exception {
        Map<String, String> params = new HashMap<>();
        for (Enumeration<String> en = request.getParameterNames(); en.hasMoreElements();) {
            String key = en.nextElement();
            params.put(key, request.getParameter(key)); // giữ nguyên, KHÔNG encode
        }

        System.out.println("vnp_TxnRef = [" + params.get("vnp_TxnRef") + "]");

        String vnpSecureHash = params.remove("vnp_SecureHash"); // loại ra trước khi hash
        params.remove("vnp_SecureHashType"); // bỏ luôn nếu có

        // ===== Sửa chỗ này =====
        // Thay vì gọi hashAllFields, sắp xếp và hash trực tiếp theo alphabetically
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = params.get(key);
            if (value != null && !value.isEmpty()) {
                hashData.append(key).append("=").append(value);
                if (i < fieldNames.size() - 1) hashData.append("&");
            }
        }

//        String signValue = vnpayService.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
//        // =======================
//
//        if (!signValue.equalsIgnoreCase(vnpSecureHash)) {
//            return ResponseEntity.badRequest().body("Invalid signature");
//        }

        Long orderId = Long.parseLong(params.get("vnp_TxnRef"));
        String responseCode = params.get("vnp_ResponseCode");

        if ("00".equals(responseCode)) {
            paymentService.updatePaymentStatus(orderId, "COMPLETED");
            return ResponseEntity.ok("Payment success");
        } else {
            paymentService.updatePaymentStatus(orderId, "FAILED");
            return ResponseEntity.ok("Payment failed");
        }
    }

}

