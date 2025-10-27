package com.coffeeshop.coffeeshop_backend.payload.request;

import com.coffeeshop.coffeeshop_backend.model.TableStatus;
import lombok.Data;

@Data
public class TableStatusUpdateRequest {
    private TableStatus status;
}
