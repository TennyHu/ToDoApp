# Todo App - RESTful API Backend

A RESTful API backend for task management built with Spring Boot, featuring priority levels and deadline tracking.

## 🚀 Features

- Complete CRUD operations for tasks
- Three-level priority system (HIGH, MEDIUM, LOW)
- Deadline management with date tracking
- RESTful API architecture
- MySQL database with JPA/Hibernate

## 🛠️ Tech Stack

- **Java 25**
- **Spring Boot 4.0**
- **Spring Data JPA**
- **MySQL**
- **Maven**

## 📂 Project Structure
```
src/main/java/com/app/todoapp/
├── controller/
│   └── TaskApiController.java    # REST API endpoints
├── services/
│   ├── TaskService.java          # Service interface
│   └── TaskServiceImpl.java      # Business Logic
├── repository/
│   └── TaskRepository.java       # JPA repository
└── models/           
    ├── Task.java                 # Entity class
    ├── Priority.java             # Enum
    └── Result.java               # Response class
```
## 🔌 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| POST | `/api/tasks` | Create new task |
| PUT | `/api/tasks/{id}` | Toggle task completion |
| DELETE | `/api/tasks/{id}` | Delete task |

## 👤 Author

**Tianyi Hu** - [GitHub](https://github.com/tennyhu) | [LinkedIn](https://www.linkedin.com/in/tianyi-tenny-hu-0711th)