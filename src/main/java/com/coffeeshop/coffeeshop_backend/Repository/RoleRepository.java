package com.coffeeshop.coffeeshop_backend.Repository;

import com.coffeeshop.coffeeshop_backend.model.ERole;
import com.coffeeshop.coffeeshop_backend.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    
    /**
     * Tìm một quyền (role) dựa trên tên của nó (dùng Enum ERole).
     * Cần thiết khi khởi tạo dữ liệu ban đầu (DataInitializer)
     * và khi gán quyền mặc định (ROLE_USER) cho người dùng mới đăng ký.
     */
    Optional<Role> findByName(ERole name);
}