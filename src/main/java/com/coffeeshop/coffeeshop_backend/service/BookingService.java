package com.coffeeshop.coffeeshop_backend.service;

import com.coffeeshop.coffeeshop_backend.model.*;
import com.coffeeshop.coffeeshop_backend.payload.request.BookingRequest;
import com.coffeeshop.coffeeshop_backend.payload.request.OrderItemRequest;
import com.coffeeshop.coffeeshop_backend.Repository.OrderRepository;
import com.coffeeshop.coffeeshop_backend.Repository.ProductRepository;
import com.coffeeshop.coffeeshop_backend.Repository.TableRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional // Đảm bảo tất cả cùng thành công, hoặc rollback nếu lỗi
    public Order createBooking(BookingRequest bookingRequest) {
        // 1. Tìm bàn
        Table table = tableRepository.findById(bookingRequest.getTableId())
                .orElseThrow(() -> new RuntimeException("Error: Table not found."));

        // 2. Kiểm tra bàn có 'AVAILABLE' (chưa có khách) không
        if (table.getStatus() != TableStatus.AVAILABLE) {
            throw new RuntimeException("Error: Table is already occupied or paid!");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;

        // 3. Xử lý các món ăn (items)
        for (OrderItemRequest itemRequest : bookingRequest.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Error: Product not found: " + itemRequest.getProductId()));
            
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice()); // Lấy giá tại thời điểm đặt
            
            orderItems.add(orderItem);
            totalPrice += (product.getPrice() * itemRequest.getQuantity());
        }

        // 4. Tạo Order mới
        Order order = new Order();
        order.setTable(table);
        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.PENDING); // Mới đặt, đang chờ
        order.setCreatedAt(LocalDateTime.now());
        
        Order savedOrder = orderRepository.save(order);

        // 5. Cập nhật trạng thái bàn thành 'OCCUPIED' (đã có khách)
        table.setStatus(TableStatus.OCCUPIED);
        tableRepository.save(table);

        return savedOrder;
    }
}