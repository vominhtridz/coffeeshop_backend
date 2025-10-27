package com.coffeeshop.coffeeshop_backend.controller;
import com.coffeeshop.coffeeshop_backend.payload.response.MessageResponse;
import com.coffeeshop.coffeeshop_backend.model.Table;
import com.coffeeshop.coffeeshop_backend.payload.request.TableRequest;
import com.coffeeshop.coffeeshop_backend.payload.request.TableStatusUpdateRequest;
import com.coffeeshop.coffeeshop_backend.Repository.TableRepository;
import com.coffeeshop.coffeeshop_backend.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tables")
@PreAuthorize("hasRole('ADMIN')")
public class TableController {

    @Autowired
    private TableService tableService;
    
    @Autowired
    private TableRepository tableRepository; // Dùng để GET

    // Admin xem tất cả các bàn
    @GetMapping
    public List<Table> getAllTables() {
        return tableRepository.findAllByOrderByTableNumberAsc();
    }

    // Admin tạo bàn mới
    @PostMapping
    public ResponseEntity<?> createTable(@RequestBody TableRequest tableRequest) {
        try {
            Table table = tableService.createTable(tableRequest);
            return ResponseEntity.ok(table);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    // Admin cập nhật trạng thái bàn
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateTableStatus(@PathVariable String id, @RequestBody TableStatusUpdateRequest statusRequest) {
        try {
            Table table = tableService.updateTableStatus(id, statusRequest.getStatus());
            return ResponseEntity.ok(table);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}