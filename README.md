# TodoApp - Spring Boot Backend Project

## Project Overview

A team collaboration Todo management system built with Spring Boot.  
It supports user registration and login, personal Todo management, and team-based collaboration.

---

## Tech Stack

| Technology | Description |
|------|------|
| Spring Boot | Backend framework |
| MyBatis | ORM framework with manual SQL |
| MySQL | Relational database |
| Redis | Caching layer |
| JWT | Authentication |
| BCrypt | Password hashing |

---

## Database Design

The system uses four tables to support users, teams, and Todos with relational associations.

```
user           User table (authentication + foreign keys)
team           Team table
user_team      User-Team junction table (many-to-many relationship)
todo           Todo table (linked to user and team)

```


## API Endpoints

```
User
    POST /user/register                 Register a new user
    POST /user/login                    Login and receive a token
    GET /user/profile                   Get user profile
    PUT /user/{id}                      Update user information

Todo
    GET /todotasks                      Get all todos
    GET /todotasks/{id}                 Get a single todo
    GET /todotasks/page                 Paginated query
    POST /todotasks                     Create a todo
    DELETE /todotasks/{id}              Delete a todo
    PUT /todotasks/{id}                 Toggle completion status

Team
    POST /team                          Create a team
    GET /team/{id}                      Get team information
    PUT /team/{id}                      Update team (admin only)
    DELETE /team/{id}                   Delete team (admin only)

UserTeam
    POST /team/{teamId}/members         Join a team
    DELETE /team/{teamId}/members       Leave a team
    GET /team/{teamId}/members          Get all team members
    GET /user/teams                     Get all teams for current user
    PUT /team/update                    Modify member role (admin only)

```

## 👤 Author

**Tianyi Hu** - [GitHub](https://github.com/tennyhu) | [LinkedIn](https://www.linkedin.com/in/tianyi-tenny-hu-0711th)
