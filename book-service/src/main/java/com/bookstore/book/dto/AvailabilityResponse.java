package com.bookstore.book.dto;

import com.bookstore.book.model.BookStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailabilityResponse {

    private Long bookId;
    private Boolean available;
    private Integer availableCopies;
    private Integer totalCopies;
    private BookStatus status;
}