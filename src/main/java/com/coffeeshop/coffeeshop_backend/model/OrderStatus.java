package com.coffeeshop.coffeeshop_backend.model;

// Enum OrderStatus
public enum OrderStatus {
    PENDING, // Đang chờ (khi bàn ở trạngái OCCUPIED)
    COMPLETED, // Hoàn thành (khi bàn ở trạng thái PAID)
    CANCELED // Bị hủy
}
