# 贡献指南

感谢你对 shopmall 项目的关注！本文档将指导你如何参与项目的开发和改进。

## 代码贡献流程

### 1. Fork 项目

点击 GitHub 页面上的 "Fork" 按钮，将项目复制到你的账户。

### 2. 克隆你的 Fork

```bash
git clone https://github.com/your-username/shopmall.git
cd shopmall
git remote add upstream https://github.com/lengYuy1/shopmall.git
```

### 3. 创建特性分支

```bash
git checkout -b feature/your-feature-name
```

分支命名规范：
- 功能特性：`feature/feature-name`
- Bug 修复：`fix/bug-name`
- 文档更新：`docs/doc-name`
- 性能优化：`perf/optimization-name`

### 4. 进行代码修改

- 遵循项目的代码风格
- 添加必要的注释和文档
- 为新功能编写单元测试

### 5. 提交代码

```bash
git add .
git commit -m "描述你的修改

详细说明（可选）
- 修改了什么
- 为什么修改
- 如何测试
"
```

提交信息规范：
- 使用现在时和命令式语气
- 首行不超过 50 个字符
- 空一行后详细说明（可选）

### 6. 推送到你的 Fork

```bash
git push origin feature/your-feature-name
```

### 7. 创建 Pull Request

在 GitHub 上点击 "New Pull Request"，选择你的分支，并提供清晰的描述：
- 修改的目的
- 修改的内容
- 相关的 Issue 编号

### 8. 代码审查和合并

等待项目维护者的审查。可能会要求你进行调整。审查通过后，你的代码将被合并到主分支。

## 开发指南

### 代码风格

遵循 Google Java 风格指南：
- 使用 4 个空格缩进
- 最大行长 100 个字符
- 类名使用 PascalCase
- 方法和变量名使用 camelCase
- 常量使用 UPPER_SNAKE_CASE

### 项目结构

```
src/main/java/com/imooc/mall/
├── controller/      # 控制器层
├── service/         # 业务逻辑层
├── model/           # 数据模型
│   ├── pojo/       # 数据库实体
│   ├── dao/        # 数据访问
│   ├── vo/         # 视图对象
│   └── request/    # 请求对象
├── config/          # 配置类
├── filter/          # 过滤器和拦截器
├── exception/       # 异常处理
├── util/            # 工具类
└── common/          # 通用类
```

### 新增功能清单

新增功能时，请确保：
- [ ] 编写业务逻辑
- [ ] 创建对应的接口
- [ ] 添加错误处理和验证
- [ ] 编写单元测试
- [ ] 更新 API 文档（Swagger 注解）
- [ ] 更新 README.md（如适用）

### 测试

运行测试：
```bash
mvn test
```

### 构建检查

在提交 PR 前，确保代码能正常构建：
```bash
mvn clean install
```

## Issue 报告

### 报告 Bug

如果发现 Bug，请创建一个 Issue，包含：
1. Bug 的描述
2. 重现步骤
3. 期望的行为
4. 实际的行为
5. 环境信息（JDK 版本、操作系统等）
6. 错误日志（如有）

### 功能请求

请清晰地描述：
1. 功能的目的
2. 预期的用法
3. 为什么这个功能很重要
4. 相关的用例

## 社区行为准则

- 尊重他人的观点和工作
- 在讨论中保持专业和礼貌
- 帮助他人解决问题
- 提供建设性的反馈
- 遵守项目的许可证和条款

## 获得帮助

- 查看现有的 Issue 和 PR
- 阅读项目文档
- 参与讨论和评论
- 联系项目维护者

## 许可证

通过提交代码，你同意将你的代码在 MIT 许可证下发布。

## 致谢

感谢所有贡献者的支持和参与！

---

**最后更新**: 2026-06-05
