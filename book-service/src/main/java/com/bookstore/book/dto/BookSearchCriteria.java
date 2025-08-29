package com.bookstore.book.dto;

import com.bookstore.book.model.BookStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookSearchCriteria {

    private String keyword;
    private String title;
    private String author;
    private String isbn;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BookStatus status;
    private Boolean available;
    private LocalDate publishedAfter;
    private LocalDate publishedBefore;
    private String language;
    private String publisher;
    private String sortBy = "title";
    private String sortDirection = "ASC";
}