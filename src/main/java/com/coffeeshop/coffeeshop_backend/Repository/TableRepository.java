package com.coffeeshop.coffeeshop_backend.Repository;

import com.coffeeshop.coffeeshop_backend.model.Table;
import com.coffeeshop.coffeeshop_backend.model.TableStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface TableRepository extends MongoRepository<Table, String> {

    /**
     * Lấy danh sách tất cả các bàn dựa trên trạng thái (status).
     * Rất quan trọng: User dùng để tìm bàn AVAILABLE (chưa có khách).
     * Admin dùng để xem bàn OCCUPIED (đã có khách) hoặc PAID (đã thanh toán).
     */
    List<Table> findByStatus(TableStatus status);

    /**
     * (Tùy chọn) Tìm bàn dựa trên số bàn.
     * Hữu ích khi admin tạo bàn mới, để kiểm tra xem số bàn đã tồn tại chưa.
     */
    Optional<Table> findByTableNumber(String tableNumber);

    /**
     * Lấy tất cả các bàn và sắp xếp theo số bàn.
     * Giúp hiển thị danh sách bàn theo thứ tự (ví dụ: Bàn 1, Bàn 2, ... Bàn 10).
     */
    List<Table> findAllByOrderByTableNumberAsc();
}