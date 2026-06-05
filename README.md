# imooc-mall - Spring Boot 电商购物商城系统

## 项目简介

imooc-mall 是一个基于 Spring Boot 的现代化电商系统，提供完整的电商业务功能，包括用户管理、商品管理、订单管理、购物车等模块。

## 核心特性

✅ **用户认证** - 支持 Session 和 JWT 双认证  
✅ **权限控制** - 基于角色的权限管理（普通用户/管理员）  
✅ **商品管理** - 完整的商品增删改查和批量操作  
✅ **订单系统** - 订单创建、支付、发货、完结等完整流程  
✅ **购物车** - 实时购物车管理和商品选择  
✅ **分类管理** - 灵活的商品分类体系  
✅ **支付二维码** - 支持支付二维码生成  
✅ **邮件验证** - 注册验证码邮件发送  
✅ **缓存优化** - Redis 缓存和 Spring Cache 支持  
✅ **API 文档** - Swagger UI 集成  

## 技术栈

| 组件 | 版本 |
|------|------|
| Spring Boot | 2.2.1.RELEASE |
| Java | 1.8 |
| MySQL | 5.7+ |
| Redis | 3.x+ |
| MyBatis | 1.3.2 |
| PageHelper | 1.2.13 |
| Swagger | 2.9.2 |
| JWT | 3.14.0 |
| Redisson | 3.13.6 |
| ZXing | 3.3.0 |

## 项目结构

```
com.imooc.mall
├── controller/              # 控制器层 (7个)
│   ├── UserController
│   ├── ProductController
│   ├── ProductAdminController
│   ├── OrderController
│   ├── OrderAdminController
│   ├── CategoryController
│   └── CartController
├── service/                 # 业务服务层
│   ├── interfaces
│   └── impl
├── model/                   # 数据模型
│   ├── pojo/               # 数据库实体
│   ├── dao/                # 数据访问对象 (MyBatis)
│   ├── vo/                 # 视图对象
│   └── request/            # 请求对象
├── config/                  # 配置类
├── filter/                  # 过滤器和AOP
├── exception/               # 异常处理
├── common/                  # 通用类和常量
└── util/                    # 工具类
```

## API 接口概览

### 🔐 用户管理接口 (8个)
- `GET /register` - 用户注册
- `POST /login` - 用户登录 (Session)
- `GET /loginWithJwt` - 用户登录 (JWT)
- `POST /user/update` - 更新用户信息
- `POST /user/logout` - 用户登出
- `POST /adminLogin` - 管理员登录
- `POST /user/sendEmail` - 发送验证邮件
- `GET /test` - 获取用户信息

### 📦 商品管理接口 (8个)
**前台接口:**
- `GET /product/detail` - 商品详情
- `GET /product/list` - 商品列表

**后台接口:**
- `POST /admin/product/add` - 添加商品
- `POST /admin/product/update` - 更新商品
- `POST /admin/product/delete` - 删除商品
- `POST /admin/product/batchUpdateSellStatus` - 批量上下架
- `POST /admin/upload/file` - 上传文件
- `POST /admin/product/list` - 后台商品列表

### 📋 订单管理接口 (8个)
**前台接口:**
- `POST /order/create` - 创建订单
- `POST /order/detail` - 订单详情
- `GET /order/cancel` - 取消订单
- `POST /order/qrcode` - 生成支付二维码
- `GET /pay` - 支付接口

**后台接口:**
- `GET /admin/order/list` - 订单列表
- `GET /admin/order/delivered` - 发货
- `GET /order/finish` - 完结订单

### 🏷️ 分类管理接口 (5个)
- `POST /admin/category/add` - 后台添加分类
- `POST /admin/category/update` - 后台更新分类
- `POST /admin/category/delete` - 删除分类
- `POST /admin/category/list` - 后台分类列表
- `POST /category/list` - 前台分类列表

### 🛒 购物车接口 (6个)
- `POST /cart/list` - 购物车列表
- `POST /cart/add` - 添加到购物车
- `POST /cart/update` - 更新购物车数量
- `POST /cart/delete` - 删除购物车项
- `POST /cart/select` - 选中/取消购物车项
- `POST /cart/selectAll` - 全选/取消全选

**总计：35个 API 接口**

## 快速开始

### 前置要求
- JDK 1.8 或更高版本
- Maven 3.6+
- MySQL 5.7+
- Redis 3.x+

### 环境配置

1. **数据库配置**：编辑 `src/main/resources/application.properties`

```properties
# 数据库连接
spring.datasource.url=jdbc:mysql://localhost:3306/imooc_mall
spring.datasource.username=root
spring.datasource.password=root

# Redis 配置
spring.redis.host=localhost
spring.redis.port=6379

# 文件上传目录
file.upload.dir=D:/upload-file/

# 邮件配置
spring.mail.host=smtp.qq.com
spring.mail.username=your-email@qq.com
spring.mail.password=your-password
```

2. **创建数据库**
```sql
CREATE DATABASE imooc_mall CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. **导入数据库脚本**
数据库脚本位于项目目录中（需要创建）

### 构建和运行

```bash
# 1. 克隆项目
git clone https://github.com/lengYuy1/shopmall.git
cd shopmall

# 2. 使用 Maven 构建
mvn clean install

# 3. 运行项目
mvn spring-boot:run

# 4. 访问应用
http://localhost:8083

# 5. 查看 Swagger API 文档
http://localhost:8083/swagger-ui.html
```

## 数据模型

### 核心实体

- **User** - 用户（用户名、密码、邮箱、角色等）
- **Product** - 商品（商品名、价格、描述、图片等）
- **Order** - 订单（订单号、用户、总价、状态等）
- **OrderItem** - 订单项（订单、商品、数量、单价等）
- **Category** - 商品分类（分类名、类型等）
- **Cart** - 购物车（用户、商品、数量、选中状态等）

## 认证和授权

### 认证方式

1. **Session 认证** - 传统的基于会话的认证方式
2. **JWT 认证** - 无状态的令牌认证方式

### 权限等级

- **普通用户** - 浏览商品、购物、订单查询等
- **管理员** - 商品管理、订单管理、分类管理等

## 核心业务流程

### 用户流程
1. 邮箱验证 → 2. 注册/登录 → 3. 更新信息 → 4. 登出

### 购物流程
1. 浏览分类 → 2. 查看商品 → 3. 加入购物车 → 4. 创建订单 → 5. 生成二维码 → 6. 支付 → 7. 查看订单状态

### 管理流程
1. 管理员登录 → 2. 管理商品/分类/订单 → 3. 处理订单（发货/完结）

## 主要功能模块

### 1. 认证和授权
- JWT 令牌生成和验证
- 用户会话管理
- 角色权限拦截

### 2. 文件上传
- 商品图片上传
- 本地文件存储
- 动态文件路径拼接

### 3. 缓存层
- Redis 整合
- Spring Cache 支持
- 验证码缓存

### 4. 邮件功能
- SMTP 邮件发送
- 验证码生成和发送
- 邮箱有效性检验

### 5. 二维码生成
- 订单支付二维码
- ZXing 库支持
- Base64 编码输出

### 6. 分页查询
- PageHelper 分页插件
- 支持多字段排序
- 灵活的分页配置

### 7. 日志和监控
- Log4j2 日志框架
- AOP Web 请求日志
- 异常统一处理

## 配置类说明

| 配置类 | 功能 |
|--------|------|
| SpringFoxConfig | Swagger API 文档配置 |
| UserFilterConfig | 普通用户过滤器配置 |
| AdminFilterConfig | 管理员过滤器配置 |
| CachingConfig | Redis 缓存配置 |
| ImoocMallWebMvcConfig | Web MVC 配置 |

## 异常处理

项目集成了全局异常处理机制，所有异常都会被统一捕获并返回标准格式的响应：

```json
{
  "code": 1001,
  "msg": "错误信息",
  "data": null
}
```

常见异常类型：
- NEED_USER_NAME - 缺少用户名
- NEED_PASSWORD - 缺少密码
- NEED_LOGIN - 需要登录
- NEED_ADMIN - 需要管理员权限
- EMAIL_ALREADY_BEEN_REGISTER - 邮箱已注册
- 等等

## 部署指南

### 生产环境配置建议

1. **数据库**
   - 使用主从复制提高可用性
   - 定期备份数据库
   - 使用连接池优化性能

2. **Redis**
   - 启用持久化 (RDB 或 AOF)
   - 配置密码保护
   - 使用 Sentinel 或 Cluster 提高可用性

3. **应用**
   - 配置 HTTPS
   - 启用请求日志
   - 设置合理的超时时间
   - 使用负载均衡

4. **安全**
   - 修改默认密钥和密码
   - 启用 CORS 访问控制
   - 验证所有用户输入
   - 定期安全审计

## 贡献指南

欢迎贡献代码！请按照以下步骤：

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/新功能`)
3. 提交更改 (`git commit -am '添加新功能'`)
4. 推送分支 (`git push origin feature/新功能`)
5. 创建 Pull Request

## 许可证

MIT License

## 联系方式

- 作者: 冷雨夜
- GitHub: [@lengYuy1](https://github.com/lengYuy1)
- 项目地址: https://github.com/lengYuy1/shopmall

## 更新日志

### v0.0.1 (初始版本)
- ✨ 完成用户管理模块
- ✨ 完成商品管理模块
- ✨ 完成订单管理模块
- ✨ 完成购物车模块
- ✨ 完成分类管理模块
- ✨ 集成 JWT 认证
- ✨ 集成 Swagger API 文档
- ✨ 集成 Redis 缓存

---

**最后更新**: 2026-06-05  
**项目状态**: 📦 正在开发中
