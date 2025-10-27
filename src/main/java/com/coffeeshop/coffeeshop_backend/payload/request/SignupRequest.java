package com.coffeeshop.coffeeshop_backend.payload.request;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    // Bạn có thể thêm các trường khác như email, fullname...
}
