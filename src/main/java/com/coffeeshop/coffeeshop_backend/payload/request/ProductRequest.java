package com.coffeeshop.coffeeshop_backend.payload.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private double price;
    private String description;
}
