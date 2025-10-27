package com.coffeeshop.coffeeshop_backend.controller;

import com.coffeeshop.coffeeshop_backend.model.Product;
import com.coffeeshop.coffeeshop_backend.model.Table;
import com.coffeeshop.coffeeshop_backend.Repository.ProductRepository;
import com.coffeeshop.coffeeshop_backend.Repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private TableRepository tableRepository;

    /**
     * API cho User xem Menu
     */
    @GetMapping("/menu")
    public List<Product> getMenu() {
        return productRepository.findAll();
    }

    /**
     * API cho User xem tất cả các bàn (và trạng thái)
     */
    @GetMapping("/tables")
    public List<Table> getAllTables() {
        // Sắp xếp theo số bàn tăng dần
        return tableRepository.findAllByOrderByTableNumberAsc();
    }
}