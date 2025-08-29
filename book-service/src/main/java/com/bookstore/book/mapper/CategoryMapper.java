package com.bookstore.book.mapper;

import com.bookstore.book.dto.*;
import com.bookstore.book.model.Category;

import java.util.List;

/**
 * Category mapping interface - implemented manually in SimpleMapperConfig
 */
public interface CategoryMapper {

    /**
     * Convert CategoryCreateRequest to Category entity
     */
    Category toEntity(CategoryCreateRequest request);

    /**
     * Convert Category entity to CategoryResponse DTO
     */
    CategoryResponse toResponse(Category category);

    /**
     * Convert Category entity to CategoryResponse DTO with children
     */
    CategoryResponse toResponseWithChildren(Category category);

    /**
     * Convert list of Category entities to CategoryResponse DTOs
     */
    List<CategoryResponse> toResponseList(List<Category> categories);

    /**
     * Update existing Category entity with CategoryUpdateRequest data
     */
    void updateEntity(CategoryUpdateRequest request, Category category);
}