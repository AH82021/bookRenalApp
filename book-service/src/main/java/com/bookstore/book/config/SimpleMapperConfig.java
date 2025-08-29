package com.bookstore.book.config;

import com.bookstore.book.dto.*;
import com.bookstore.book.mapper.BookMapper;
import com.bookstore.book.mapper.CategoryMapper;
import com.bookstore.book.model.Book;
import com.bookstore.book.model.BookStatus;
import com.bookstore.book.model.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class SimpleMapperConfig {

    @Bean
    @Primary
    public BookMapper bookMapper() {
        return new SimpleBookMapper();
    }

    @Bean
    @Primary
    public CategoryMapper categoryMapper() {
        return new SimpleCategoryMapper();
    }

    private static class SimpleBookMapper implements BookMapper {

        @Override
        public Book toEntity(BookCreateRequest request) {
            if (request == null) {
                return null;
            }

            return Book.builder()
                    .title(request.getTitle())
                    .author(request.getAuthor())
                    .isbn(request.getIsbn())
                    .description(request.getDescription())
                    .price(request.getPrice())
                    .rentalPrice(request.getRentalPrice())
                    .publicationDate(request.getPublicationDate())
                    .publisher(request.getPublisher())
                    .pages(request.getPages())
                    .language(request.getLanguage())
                    .coverImageUrl(request.getCoverImageUrl())
                    .totalCopies(request.getTotalCopies())
                    .availableCopies(request.getTotalCopies())
                    .status(BookStatus.AVAILABLE)
                    .categories(new HashSet<>())
                    .build();
        }

        @Override
        public BookResponse toResponse(Book book) {
            if (book == null) {
                return null;
            }

            return BookResponse.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .description(book.getDescription())
                    .price(book.getPrice())
                    .rentalPrice(book.getRentalPrice())
                    .publicationDate(book.getPublicationDate())
                    .publisher(book.getPublisher())
                    .pages(book.getPages())
                    .language(book.getLanguage())
                    .coverImageUrl(book.getCoverImageUrl())
                    .totalCopies(book.getTotalCopies())
                    .availableCopies(book.getAvailableCopies())
                    .status(book.getStatus())
                    .createdAt(book.getCreatedAt())
                    .updatedAt(book.getUpdatedAt())
                    .isAvailable(book.isAvailable())
                    .categories(mapCategories(book.getCategories()))
                    .build();
        }

        @Override
        public List<BookResponse> toResponseList(List<Book> books) {
            if (books == null) {
                return null;
            }
            return books.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        }

        @Override
        public void updateEntity(BookUpdateRequest request, Book book) {
            if (request == null || book == null) {
                return;
            }

            if (request.getTitle() != null) {
                book.setTitle(request.getTitle());
            }
            if (request.getAuthor() != null) {
                book.setAuthor(request.getAuthor());
            }
            if (request.getDescription() != null) {
                book.setDescription(request.getDescription());
            }
            if (request.getPrice() != null) {
                book.setPrice(request.getPrice());
            }
            if (request.getRentalPrice() != null) {
                book.setRentalPrice(request.getRentalPrice());
            }
            if (request.getPublicationDate() != null) {
                book.setPublicationDate(request.getPublicationDate());
            }
            if (request.getPublisher() != null) {
                book.setPublisher(request.getPublisher());
            }
            if (request.getPages() != null) {
                book.setPages(request.getPages());
            }
            if (request.getLanguage() != null) {
                book.setLanguage(request.getLanguage());
            }
            if (request.getCoverImageUrl() != null) {
                book.setCoverImageUrl(request.getCoverImageUrl());
            }
            if (request.getStatus() != null) {
                book.setStatus(request.getStatus());
            }
            if (request.getTotalCopies() != null) {
                book.setTotalCopies(request.getTotalCopies());
            }
            if (request.getAvailableCopies() != null) {
                book.setAvailableCopies(request.getAvailableCopies());
            }
        }

        @Override
        public Set<CategoryResponse> mapCategories(Set<Category> categories) {
            if (categories == null) {
                return null;
            }
            return categories.stream()
                    .map(this::mapCategory)
                    .collect(Collectors.toSet());
        }

        @Override
        public CategoryResponse mapCategory(Category category) {
            if (category == null) {
                return null;
            }

            CategoryResponse.CategoryResponseBuilder builder = CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .slug(category.getSlug())
                    .description(category.getDescription())
                    .createdAt(category.getCreatedAt())
                    .bookCount(0); // Don't access books collection to avoid lazy loading issues

            // Safely handle parent relationship
            if (category.getParent() != null) {
                builder.parentId(category.getParent().getId())
                        .parentName(category.getParent().getName());
            }

            return builder.build();
        }
    }

    private static class SimpleCategoryMapper implements CategoryMapper {

        @Override
        public Category toEntity(CategoryCreateRequest request) {
            if (request == null) {
                return null;
            }

            return Category.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .build();
        }

        @Override
        public CategoryResponse toResponse(Category category) {
            if (category == null) {
                return null;
            }

            CategoryResponse.CategoryResponseBuilder builder = CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .slug(category.getSlug())
                    .description(category.getDescription())
                    .createdAt(category.getCreatedAt())
                    .bookCount(0); // Don't access books collection to avoid lazy loading

            if (category.getParent() != null) {
                builder.parentId(category.getParent().getId())
                        .parentName(category.getParent().getName());
            }

            return builder.build();
        }

        @Override
        public CategoryResponse toResponseWithChildren(Category category) {
            if (category == null) {
                return null;
            }

            CategoryResponse.CategoryResponseBuilder builder = CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .slug(category.getSlug())
                    .description(category.getDescription())
                    .createdAt(category.getCreatedAt())
                    .bookCount(0); // Don't access books collection to avoid lazy loading

            if (category.getParent() != null) {
                builder.parentId(category.getParent().getId())
                        .parentName(category.getParent().getName());
            }

            return builder.build();
        }

        @Override
        public List<CategoryResponse> toResponseList(List<Category> categories) {
            if (categories == null) {
                return null;
            }
            return categories.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        }

        @Override
        public void updateEntity(CategoryUpdateRequest request, Category category) {
            if (request == null || category == null) {
                return;
            }

            if (request.getName() != null) {
                category.setName(request.getName());
            }
            if (request.getDescription() != null) {
                category.setDescription(request.getDescription());
            }
        }
    }
}
