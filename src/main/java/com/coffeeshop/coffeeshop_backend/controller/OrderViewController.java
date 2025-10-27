package com.coffeeshop.coffeeshop_backend.controller;

import com.coffeeshop.coffeeshop_backend.model.Order;
import com.coffeeshop.coffeeshop_backend.model.OrderStatus;
import com.coffeeshop.coffeeshop_backend.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
public class OrderViewController {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * API cho Admin xem các bàn đang CÓ KHÁCH (OCCUPIED)
     * Logic: Lấy tất cả các Order đang ở trạng thái PENDING
     * (Vì PENDING đồng nghĩa với bàn đang OCCUPIED,
     * COMPLETED đồng nghĩa với bàn đang PAID)
     */
    @GetMapping("/active")
    public List<Order> getActiveOrders() {
        // Trong model Order đã có @DBRef tới Table,
        // nên khi trả về Order sẽ có cả thông tin bàn
        return orderRepository.findByStatus(OrderStatus.PENDING);
    }
}