package com.coffeeshop.coffeeshop_backend.payload.request;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {
    private String tableId;
    private List<OrderItemRequest> items;
}
