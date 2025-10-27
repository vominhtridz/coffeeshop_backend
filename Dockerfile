# Sử dụng base image Java 21 (hoặc 17 nếu bạn dùng 17)
FROM eclipse-temurin:21-jdk-jammy

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép file pom.xml và mvnw wrapper để tải dependencies (tận dụng cache layer)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Tải dependencies
RUN ./mvnw dependency:go-offline

# Sao chép mã nguồn
COPY src ./src

# Build ứng dụng (BẮT BUỘC PHẢI CÓ "clean" ở ĐÂY)
# "clean" sẽ xóa code cũ đã biên dịch
RUN ./mvnw clean package -DskipTests

# Expose port mà Spring Boot chạy
EXPOSE 8080

# Lệnh chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "target/coffeeshop-backend-0.0.1-SNAPSHOT.jar"]
