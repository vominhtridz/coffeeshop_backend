package com.coffeeshop.coffeeshop_backend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyReportResponse {
    private double totalRevenue; // Tổng doanh thu
    private long totalOrders;    // Tổng số đơn (khách)
}