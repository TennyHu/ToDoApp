package com.app.todoapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean<T> {
    private Long total;         // total number
    private Integer totalPages; // total page
    private Integer currentPage; // current page
    private Integer pageSize;   // # of data each page
    private List<T> records;    // data for current page

    // convert Spring Page to Page Bean
    public PageBean(org.springframework.data.domain.Page<T> page) {
        this.total = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;  // Spring page is from 0
        this.pageSize = page.getSize();
        this.records = page.getContent();
    }
}
