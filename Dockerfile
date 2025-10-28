# STAGE 1: Build (Giai đoạn Xây dựng ứng dụng)
# Dùng base image có JDK (Java Development Kit)
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

# Sao chép các file cần thiết cho build
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# !!! FIX LỖI "PERMISSION DENIED" !!!
# Cấp quyền thực thi (execute) cho file mvnw
RUN chmod +x ./mvnw

# Tải dependencies (tận dụng cache của Docker)
RUN ./mvnw dependency:go-offline

# Sao chép mã nguồn
COPY src ./src

# Build ứng dụng (clean và package)
# Bỏ qua test vì đã test ở CI/local
RUN ./mvnw clean package -DskipTests

# ---------------------------------------------------

# STAGE 2: Run (Giai đoạn Chạy ứng dụng)
# Dùng base image JRE (Java Runtime Environment) - nhỏ gọn hơn
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Đổi tên file .jar để dễ gọi
ARG JAR_FILE=target/coffeeshop-backend-0.0.1-SNAPSHOT.jar

# Chỉ copy file .jar đã build từ STAGE 1 (builder)
COPY --from=builder /app/${JAR_FILE} app.jar

# Expose port mà Spring Boot chạy
EXPOSE 8080

# Lệnh chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]
