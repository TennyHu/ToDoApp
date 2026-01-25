# Todo 应用 - RESTful API 后端

基于 Spring Boot 开发的任务管理 RESTful API 后端，支持优先级和截止日期管理。

## 🚀 功能特性

- 完整的任务增删改查操作
- 三级优先级系统（高、中、低）
- 截止日期管理
- RESTful API 架构
- MySQL 数据库 + JPA/Hibernate

## 🛠️ 技术栈

- **Java 24**
- **Spring Boot 4.0**
- **Spring Data JPA**
- **MySQL**
- **Maven**

## 📂 项目结构
```
src/main/java/com/app/todoapp/
├── controller/
│   └── TaskApiController.java    # REST API 端点
├── services/
│   ├── TaskService.java          # 服务接口
│   └── TaskServiceImpl.java      # 业务逻辑
├── repository/
│   └── TaskRepository.java       # JPA 数据仓库
└── pojo/
    ├── Task.java                 # 实体类
    ├── Priority.java             # 枚举
    └── Result.java               # 统一响应格式
```
## 🔌 API 接口

| 方法 | 端点 | 描述 |
|------|------|------|
| GET | `/api/tasks` | 获取所有任务 |
| GET | `/api/tasks/{id}` | 根据ID获取任务 |
| POST | `/api/tasks` | 创建新任务 |
| PUT | `/api/tasks/{id}` | 切换任务完成状态 |
| DELETE | `/api/tasks/{id}` | 删除任务 |

## 👤 作者

**胡天依** - [GitHub](https://github.com/tennyhu) | [LinkedIn](https://www.linkedin.com/in/tianyi-tenny-hu-0711th)