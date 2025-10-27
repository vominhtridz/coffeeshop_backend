package com.coffeeshop.coffeeshop_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.List;
import java.time.LocalDateTime;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;

    @DBRef
    private Table table; // Bàn được đặt

    private List<OrderItem> items; // Danh sách món
    private double totalPrice;
    private OrderStatus status; // Enum: PENDING, COMPLETED, CANCELED
    private LocalDateTime createdAt;
}
// Xóa các class/enum khác khỏi file này
