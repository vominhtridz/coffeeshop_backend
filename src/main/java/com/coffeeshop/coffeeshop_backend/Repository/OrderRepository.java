package com.coffeeshop.coffeeshop_backend.Repository;

import com.coffeeshop.coffeeshop_backend.model.Order;
import com.coffeeshop.coffeeshop_backend.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    /**
     * Tìm một đơn hàng (order) dựa trên ID của bàn (tableId) VÀ trạng thái của đơn hàng.
     * Cực kỳ quan trọng: Dùng để lấy chi tiết đơn hàng đang 'PENDING' (chưa thanh toán)
     * của một bàn đang 'OCCUPIED'.
     */
    Optional<Order> findByTableIdAndStatus(String tableId, OrderStatus status);

    /**
     * Lấy tất cả các đơn hàng theo một trạng thái cụ thể.
     * Ví dụ: Lấy tất cả các đơn 'PENDING' để hiển thị cho Admin.
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * Lấy tất cả các đơn hàng có trạng thái nhất định VÀ được tạo trong một khoảng thời gian.
     * Đây là phương thức cốt lõi cho chức năng "Thống kê báo cáo".
     * Ví dụ: Lấy các đơn 'COMPLETED' (đã thanh toán) từ 0h:00 đến 23:59
     * của ngày hôm nay để tính tổng doanh thu.
     * (startOfDay) và (endOfDay) sẽ được truyền vào từ Service.
     */
    List<Order> findByStatusAndCreatedAtBetween(OrderStatus status, LocalDateTime startOfDay, LocalDateTime endOfDay);
}