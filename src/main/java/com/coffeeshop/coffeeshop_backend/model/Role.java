package com.coffeeshop.coffeeshop_backend.model; // Đảm bảo bạn có dòng package

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    private ERole name; // Dòng này vẫn giữ nguyên
}

