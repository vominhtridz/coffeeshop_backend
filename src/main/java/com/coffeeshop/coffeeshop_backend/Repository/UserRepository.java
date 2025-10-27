package com.coffeeshop.coffeeshop_backend.Repository;

import com.coffeeshop.coffeeshop_backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    
    /**
     * Tìm một người dùng dựa trên username.
     * Dùng cho chức năng đăng nhập và UserDetailsService của Spring Security.
     * Optional<T> là một cách hay để xử lý trường hợp không tìm thấy (tránh NullPointerException).
     */
    Optional<User> findByUsername(String username);

    /**
     * Kiểm tra xem một username đã tồn tại trong CSDL hay chưa.
     * Dùng cho chức năng đăng ký để tránh trùng lặp.
     */
    Boolean existsByUsername(String username);
}