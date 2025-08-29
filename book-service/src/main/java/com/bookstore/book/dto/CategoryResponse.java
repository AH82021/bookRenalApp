package com.bookstore.book.dto;

import lombok.Data;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoryResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private Long parentId;
    private String parentName;
    private Integer bookCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}