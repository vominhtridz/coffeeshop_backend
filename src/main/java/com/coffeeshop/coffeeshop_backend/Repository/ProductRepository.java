package com.coffeeshop.coffeeshop_backend.Repository;

import com.coffeeshop.coffeeshop_backend.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    /**
     * (Tùy chọn) Tìm sản phẩm theo tên.
     * Hữu ích nếu bạn muốn kiểm tra trùng lặp tên sản phẩm khi admin thêm món mới.
     */
    Optional<Product> findByName(String name);

    // Các phương thức như findAll(), findById(), save(), deleteById()
    // đã được MongoRepository cung cấp sẵn.
}