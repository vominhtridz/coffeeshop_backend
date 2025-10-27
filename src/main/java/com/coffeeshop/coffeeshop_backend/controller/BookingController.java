package com.coffeeshop.coffeeshop_backend.controller;

import com.coffeeshop.coffeeshop_backend.model.Order;
import com.coffeeshop.coffeeshop_backend.payload.request.BookingRequest;
import com.coffeeshop.coffeeshop_backend.payload.response.MessageResponse;
import com.coffeeshop.coffeeshop_backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')") // Chỉ User đã đăng nhập mới được đặt bàn
public class BookingController {

    @Autowired
    private BookingService bookingService;

    /**
     * API User đặt bàn và gọi món
     */
    @PostMapping("/orders")
    public ResponseEntity<?> placeOrder(@RequestBody BookingRequest bookingRequest) {
        try {
            Order order = bookingService.createBooking(bookingRequest);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}