package com.coffeeshop.coffeeshop_backend.model;

import lombok.Data;

// Đây là class nhúng, không phải document riêng
@Data
public class OrderItem {
    private String productId;
    private String productName;
    private int quantity;
    private double price; // Giá tại thời điểm đặt
}
