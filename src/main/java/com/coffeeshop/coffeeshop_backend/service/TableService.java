package com.coffeeshop.coffeeshop_backend.service;

import com.coffeeshop.coffeeshop_backend.model.*;
import com.coffeeshop.coffeeshop_backend.payload.request.TableRequest;
import com.coffeeshop.coffeeshop_backend.Repository.OrderRepository;
import com.coffeeshop.coffeeshop_backend.Repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private OrderRepository orderRepository;

    // Tạo bàn mới
    public Table createTable(TableRequest tableRequest) {
        if (tableRepository.findByTableNumber(tableRequest.getTableNumber()).isPresent()) {
            throw new RuntimeException("Error: Table number already exists!");
        }
        Table table = new Table();
        table.setTableNumber(tableRequest.getTableNumber());
        table.setStatus(TableStatus.AVAILABLE); // Mặc định là 'Chưa có khách'
        return tableRepository.save(table);
    }

    // Cập nhật trạng thái bàn
    @Transactional
    public Table updateTableStatus(String id, TableStatus newStatus) {
        Table table = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Table not found!"));
        
        // Logic quan trọng: Khi Admin cập nhật bàn là "ĐÃ THANH TOÁN"
        if (newStatus == TableStatus.PAID && table.getStatus() == TableStatus.OCCUPIED) {
            // Tìm đơn hàng (Order) đang 'PENDING' của bàn này
            Order order = orderRepository.findByTableIdAndStatus(table.getId(), OrderStatus.PENDING)
                    .orElse(null);
            
            if (order != null) {
                // Chuyển trạng thái đơn hàng thành 'COMPLETED' để báo cáo
                order.setStatus(OrderStatus.COMPLETED);
                orderRepository.save(order);
            }
        }
        
        // Nếu admin dọn bàn (từ PAID -> AVAILABLE)
        if (newStatus == TableStatus.AVAILABLE && table.getStatus() == TableStatus.PAID) {
            // Không cần xử lý order, chỉ cần cập nhật trạng thái bàn
        }

        table.setStatus(newStatus);
        return tableRepository.save(table);
    }
}