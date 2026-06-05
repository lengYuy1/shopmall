# 部署指南

## 环境要求

- JDK 1.8 或更高版本
- Maven 3.6+
- MySQL 5.7+
- Redis 3.x+

## 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/lengYuy1/shopmall.git
cd shopmall
```

### 2. 数据库配置

编辑 `src/main/resources/application.properties`：

```properties
# 数据库连接
spring.datasource.url=jdbc:mysql://localhost:3306/imooc_mall?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=root

# Redis
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=

# 文件上传
file.upload.dir=D:/upload-file/
```

### 3. 创建数据库

```sql
CREATE DATABASE imooc_mall CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 4. 构建项目

```bash
mvn clean install
```

### 5. 运行项目

```bash
mvn spring-boot:run
```

或者：

```bash
java -jar target/mall-0.0.1-SNAPSHOT.jar
```

应用将在 `http://localhost:8083` 启动

### 6. 查看API文档

访问 Swagger UI：http://localhost:8083/swagger-ui.html

## 配置说明

### application.properties

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| server.port | 服务器端口 | 8083 |
| spring.datasource.url | 数据库连接 | localhost:3306/imooc_mall |
| spring.redis.host | Redis主机 | localhost |
| spring.redis.port | Redis端口 | 6379 |
| file.upload.dir | 文件上传目录 | D:/upload-file/ |

### 邮件配置

编辑 `application.properties` 中的邮件部分：

```properties
spring.mail.host=smtp.qq.com
spring.mail.port=587
spring.mail.username=your-email@qq.com
spring.mail.password=your-app-password
```

## 常见问题

### Q1: 数据库连接失败
- 确保MySQL已启动
- 检查连接字符串是否正确
- 确保用户名密码正确

### Q2: Redis连接失败
- 确保Redis服务已启动
- 检查host和port是否正确
- 如需密码，在配置中添加password

### Q3: 文件上传失败
- 确保目录 `D:/upload-file/` 存在
- 检查应用是否有写入权限
- Windows用户可使用 `C:\uploads` 等路径

### Q4: 邮件发送失败
- 确保使用的是应用密码（非账户密码）
- 检查SMTP配置是否正确
- 某些邮箱需要开启SMTP功能

## 生产环境部署

### Docker 部署

创建 `Dockerfile`：

```dockerfile
FROM openjdk:8-jdk-slim
COPY target/mall-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8083
```

构建和运行：

```bash
docker build -t shopmall:1.0 .
docker run -p 8083:8083 shopmall:1.0
```

### Linux 服务启动

创建 systemd 服务文件 `/etc/systemd/system/shopmall.service`：

```ini
[Unit]
Description=ShopMall Service
After=network.target

[Service]
Type=simple
User=app
WorkingDirectory=/opt/shopmall
ExecStart=/usr/local/jdk1.8/bin/java -jar /opt/shopmall/mall-0.0.1-SNAPSHOT.jar
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

启动服务：

```bash
sudo systemctl start shopmall
sudo systemctl enable shopmall
```

## 性能优化建议

### 1. 数据库连接池
在 `application.properties` 添加：

```properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000
```

### 2. Redis 缓存优化
- 启用 Redis 持久化
- 配置合理的过期时间
- 使用 Redis Cluster 提高可用性

### 3. 日志优化
编辑 `src/main/resources/log4j2.xml`，调整日志级别

### 4. JVM 参数优化

```bash
java -Xmx1024m -Xms512m -XX:+UseG1GC jar app.jar
```

## 监控和维护

### 查看日志

```bash
tail -f logs/imooc-mall.log
```

### 健康检查

添加 Actuator 依赖以进行健康检查：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

访问：http://localhost:8083/actuator/health

## 相关资源

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [MyBatis 官方文档](https://mybatis.org/)
- [Redis 官方网站](https://redis.io/)

## 支持

有任何问题，请提交 Issue 或 Pull Request。

---

**最后更新**: 2026-06-05
