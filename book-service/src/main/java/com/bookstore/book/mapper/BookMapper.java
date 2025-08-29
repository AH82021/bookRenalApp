package com.bookstore.book.mapper;

import com.bookstore.book.dto.*;
import com.bookstore.book.model.Book;
import com.bookstore.book.model.Category;

import java.util.List;
import java.util.Set;

/**
 * Book mapping interface - implemented manually in SimpleMapperConfig
 */
public interface BookMapper {

    /**
     * Convert BookCreateRequest to Book entity
     */
    Book toEntity(BookCreateRequest request);

    /**
     * Convert Book entity to BookResponse DTO
     */
    BookResponse toResponse(Book book);

    /**
     * Convert list of Book entities to BookResponse DTOs
     */
    List<BookResponse> toResponseList(List<Book> books);

    /**
     * Update existing Book entity with BookUpdateRequest data
     */
    void updateEntity(BookUpdateRequest request, Book book);

    /**
     * Map categories to category responses
     */
    Set<CategoryResponse> mapCategories(Set<Category> categories);

    /**
     * Map single category to category response
     */
    CategoryResponse mapCategory(Category category);
}