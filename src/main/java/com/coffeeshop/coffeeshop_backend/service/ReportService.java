package com.coffeeshop.coffeeshop_backend.service;

import com.coffeeshop.coffeeshop_backend.model.Order;
import com.coffeeshop.coffeeshop_backend.model.OrderStatus;
import com.coffeeshop.coffeeshop_backend.payload.response.DailyReportResponse;
import com.coffeeshop.coffeeshop_backend.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private OrderRepository orderRepository;

    public DailyReportResponse getDailyReport() {
        // Lấy thời điểm đầu ngày (00:00:00)
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        // Lấy thời điểm cuối ngày (23:59:59)
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        // Lấy tất cả các đơn hàng ĐÃ HOÀN THÀNH (COMPLETED) trong ngày
        // (Status này được set ở TableService khi Admin nhấn 'Đã Thanh Toán')
        List<Order> completedOrders = orderRepository.findByStatusAndCreatedAtBetween(
                OrderStatus.COMPLETED,
                startOfDay,
                endOfDay
        );

        // Tính tổng doanh thu
        double totalRevenue = completedOrders.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();

        // Tính tổng số lượng khách (đơn hàng)
        long totalOrders = completedOrders.size();

        return new DailyReportResponse(totalRevenue, totalOrders);
    }
}