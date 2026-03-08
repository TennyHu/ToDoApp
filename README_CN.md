# TodoApp - Spring Boot 后端项目

## 项目简介

基于 Spring Boot 的团队协作 Todo 管理系统，支持用户注册登录、个人 Todo 管理、团队创建与协作。

---

## 技术栈

| 技术 | 说明 |
|------|------|
| Spring Boot | 后端框架 |
| MyBatis | ORM 框架，手写 SQL |
| MySQL | 关系型数据库 |
| Redis | 缓存层 |
| JWT | 身份验证 |
| BCrypt | 密码加密 |

---

## 数据库设计

四张表，支持用户、团队、Todo 的多表关联：

```
user          用户表（身份验证 + 外键）
team          团队表（外键）
user_team     用户-团队中间表（多对多）
todo          待办事项表（关联 user 和 team）
```

## API 汇总
```
User
  POST   /user/register                        注册
  POST   /user/login                           登录，返回 token
  GET    /user/profile                         查看个人信息
  PUT    /user/{id}                            更新个人信息

Todo
  GET    /todotasks                            查所有 todo
  GET    /todotasks/{id}                       查单个 todo
  GET    /todotasks/page                       分页查询
  POST   /todotasks                            创建 todo
  DELETE /todotasks/{id}                       删除 todo
  PUT    /todotasks/{id}                       切换完成状态

Team
  POST   /team                                 创建团队
  GET    /team/{id}                            查团队信息
  PUT    /team/{id}                            更新团队（admin）
  DELETE /team/{id}                            解散团队（admin）

UserTeam
  POST   /team/{teamId}/members                加入团队
  DELETE /team/{teamId}/members                退出团队
  GET    /team/{teamId}/members                查团队所有成员
  GET    /user/teams                           查当前用户的所有团队
  PUT    /team/update                          修改成员角色（admin）
```

## 👤 作者

**胡天依** - [GitHub](https://github.com/tennyhu) | [LinkedIn](https://www.linkedin.com/in/tianyi-tenny-hu-0711th)