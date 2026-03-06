//package com.app.todoapp.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Entity
//@Data
//public class Task {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    private String title;
//    private boolean completed;
//    private LocalDate deadline;
//
//    @Enumerated(EnumType.STRING)
//    private Priority priority = Priority.low;       // by default, low priority level
//
//
//
//}
