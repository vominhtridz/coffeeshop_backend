package com.coffeeshop.coffeeshop_backend.config;

import com.coffeeshop.coffeeshop_backend.model.ERole;
import com.coffeeshop.coffeeshop_backend.model.Role;
import com.coffeeshop.coffeeshop_backend.model.User;
import com.coffeeshop.coffeeshop_backend.Repository.RoleRepository;
import com.coffeeshop.coffeeshop_backend.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Lớp này sẽ tự động chạy khi ứng dụng khởi động.
 * Nhiệm vụ: Khởi tạo các Role và tài khoản Admin (root) mặc định.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Bean này đã được tạo ở SecurityConfig (Bước 4)

    @Override
    public void run(String... args) throws Exception {
        logger.info("Bắt đầu khởi tạo dữ liệu mẫu...");

        // 1. Khởi tạo ROLE_USER
        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
            Role userRole = new Role();
            userRole.setName(ERole.ROLE_USER);
            roleRepository.save(userRole);
            logger.info("Đã tạo ROLE_USER");
        }

        // 2. Khởi tạo ROLE_ADMIN
        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName(ERole.ROLE_ADMIN);
            roleRepository.save(adminRole);
            logger.info("Đã tạo ROLE_ADMIN");
        }

        // 3. Khởi tạo tài khoản 'root' (ADMIN) mặc định
        if (!userRepository.existsByUsername("root")) {
            // Lấy ROLE_ADMIN từ CSDL (đã được tạo ở trên)
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy ROLE_ADMIN."));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole); // Gán quyền admin cho tài khoản root

            // Tạo đối tượng User
            User rootUser = new User();
            rootUser.setUsername("root");
            
            // Mã hóa mật khẩu trước khi lưu
            rootUser.setPassword(passwordEncoder.encode("123456"));

            rootUser.setRoles(roles); // Set quyền

            // Lưu tài khoản root vào CSDL
            userRepository.save(rootUser);
            logger.info("Đã tạo tài khoản 'root' (ADMIN) mặc định.");
        }

        logger.info("Hoàn tất khởi tạo dữ liệu.");
    }
}