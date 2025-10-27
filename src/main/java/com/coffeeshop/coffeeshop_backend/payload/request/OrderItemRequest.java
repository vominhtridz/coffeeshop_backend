package com.coffeeshop.coffeeshop_backend.payload.request;

import lombok.Data;

@Data
public class OrderItemRequest {
    private String productId;
    private int quantity;
}
