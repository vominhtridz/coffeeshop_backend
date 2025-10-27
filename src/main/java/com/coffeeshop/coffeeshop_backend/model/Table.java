package com.coffeeshop.coffeeshop_backend.model; // Bạn cần dòng package này

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "tables")
public class Table {
    @Id
    private String id;
    private String tableNumber; // Ví dụ: "Bàn 1", "Bàn 2"
    private TableStatus status; // Enum: AVAILABLE, OCCUPIED, PAID
}

// XÓA PHẦN ENUM KHỎI ĐÂY