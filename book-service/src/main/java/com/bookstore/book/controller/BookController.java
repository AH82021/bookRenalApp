package com.bookstore.book.controller;



import com.bookstore.book.dto.*;
import com.bookstore.book.model.BookStatus;
import com.bookstore.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Books", description = "Book management operations")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Create a new book", description = "Creates a new book with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Book with ISBN already exists")
    })
    public ResponseEntity<BookResponse> createBook(
            @Valid @RequestBody BookCreateRequest request) {
        log.info("Creating new book with title: {}", request.getTitle());
        BookResponse response = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieves a book by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> getBookById(
            @Parameter(description = "Book ID") @PathVariable Long id) {
        log.debug("Fetching book with ID: {}", id);
        BookResponse response = bookService.getBookById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Get book by ISBN", description = "Retrieves a book by its ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> getBookByIsbn(
            @Parameter(description = "Book ISBN") @PathVariable String isbn) {
        log.debug("Fetching book with ISBN: {}", isbn);
        BookResponse response = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Updates an existing book with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> updateBook(
            @Parameter(description = "Book ID") @PathVariable Long id,
            @Valid @RequestBody BookUpdateRequest request) {
        log.info("Updating book with ID: {}", id);
        BookResponse response = bookService.updateBook(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Soft deletes a book by marking it as deleted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "Book ID") @PathVariable Long id) {
        log.info("Deleting book with ID: {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieves all books with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully")
    })
    public ResponseEntity<Page<BookResponse>> getAllBooks(
            @PageableDefault(size = 20, sort = "title") Pageable pageable) {
        log.debug("Fetching all books with pagination: {}", pageable);
        Page<BookResponse> response = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search books", description = "Searches books by keyword in title, author, or description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully")
    })
    public ResponseEntity<Page<BookResponse>> searchBooks(
            @Parameter(description = "Search keyword") @RequestParam String keyword,
            @PageableDefault(size = 20, sort = "title") Pageable pageable) {
        log.debug("Searching books with keyword: {}", keyword);
        Page<BookResponse> response = bookService.searchBooks(keyword, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/advanced")
    @Operation(summary = "Advanced book search", description = "Searches books with multiple criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully")
    })
    public ResponseEntity<Page<BookResponse>> searchBooksAdvanced(
            @Parameter(description = "Book title") @RequestParam(required = false) String title,
            @Parameter(description = "Book author") @RequestParam(required = false) String author,
            @Parameter(description = "Book ISBN") @RequestParam(required = false) String isbn,
            @Parameter(description = "Category ID") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Book status") @RequestParam(required = false) BookStatus status,
            @Parameter(description = "Minimum price") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Publisher") @RequestParam(required = false) String publisher,
            @Parameter(description = "Language") @RequestParam(required = false) String language,
            @Parameter(description = "Available books only") @RequestParam(required = false) Boolean available,
            @PageableDefault(size = 20, sort = "title") Pageable pageable) {
        log.debug("Advanced search with multiple criteria");
        Page<BookResponse> response = bookService.searchBooksAdvanced(
                title, author, isbn, categoryId, status, minPrice, maxPrice,
                publisher, language, available, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get books by category", description = "Retrieves books belonging to a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<Page<BookResponse>> getBooksByCategory(
            @Parameter(description = "Category ID") @PathVariable Long categoryId,
            @PageableDefault(size = 20, sort = "title") Pageable pageable) {
        log.debug("Fetching books for category ID: {}", categoryId);
        Page<BookResponse> response = bookService.getBooksByCategory(categoryId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get books by status", description = "Retrieves books with a specific status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully")
    })
    public ResponseEntity<Page<BookResponse>> getBooksByStatus(
            @Parameter(description = "Book status") @PathVariable BookStatus status,
            @PageableDefault(size = 20, sort = "title") Pageable pageable) {
        log.debug("Fetching books with status: {}", status);
        Page<BookResponse> response = bookService.getBooksByStatus(status, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    @Operation(summary = "Get available books", description = "Retrieves all available books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available books retrieved successfully")
    })
    public ResponseEntity<Page<BookResponse>> getAvailableBooks(
            @PageableDefault(size = 20, sort = "title") Pageable pageable) {
        log.debug("Fetching available books");
        Page<BookResponse> response = bookService.getAvailableBooks(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock books", description = "Retrieves books with low inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Low stock books retrieved successfully")
    })
    public ResponseEntity<Page<BookResponse>> getLowStockBooks(
            @Parameter(description = "Stock threshold") @RequestParam(defaultValue = "5") int threshold,
            @PageableDefault(size = 20, sort = "availableCopies") Pageable pageable) {
        log.debug("Fetching low stock books with threshold: {}", threshold);
        Page<BookResponse> response = bookService.getLowStockBooks(threshold, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/inventory")
    @Operation(summary = "Update book inventory", description = "Updates the inventory counts for a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid inventory data"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> updateInventory(
            @Parameter(description = "Book ID") @PathVariable Long id,
            @Parameter(description = "Total copies") @RequestParam Integer totalCopies,
            @Parameter(description = "Available copies") @RequestParam Integer availableCopies) {
        log.info("Updating inventory for book ID: {}", id);
        BookResponse response = bookService.updateInventory(id, totalCopies, availableCopies);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/reserve")
    @Operation(summary = "Reserve book copies", description = "Reserves the specified number of book copies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Copies reserved successfully"),
            @ApiResponse(responseCode = "400", description = "Insufficient inventory"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> reserveCopies(
            @Parameter(description = "Book ID") @PathVariable Long id,
            @Parameter(description = "Number of copies to reserve") @RequestParam Integer quantity) {
        log.info("Reserving {} copies for book ID: {}", quantity, id);
        BookResponse response = bookService.reserveCopies(id, quantity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/release")
    @Operation(summary = "Release book copies", description = "Releases the specified number of book copies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Copies released successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid release quantity"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> releaseCopies(
            @Parameter(description = "Book ID") @PathVariable Long id,
            @Parameter(description = "Number of copies to release") @RequestParam Integer quantity) {
        log.info("Releasing {} copies for book ID: {}", quantity, id);
        BookResponse response = bookService.releaseCopies(id, quantity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/categories")
    @Operation(summary = "Add categories to book", description = "Adds the specified categories to a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories added successfully"),
            @ApiResponse(responseCode = "404", description = "Book or category not found")
    })
    public ResponseEntity<BookResponse> addCategoriesToBook(
            @Parameter(description = "Book ID") @PathVariable Long id,
            @RequestBody Set<Long> categoryIds) {
        log.info("Adding categories to book ID: {}", id);
        BookResponse response = bookService.addCategoriesToBook(id, categoryIds);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/categories")
    @Operation(summary = "Remove categories from book", description = "Removes the specified categories from a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories removed successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> removeCategoriesFromBook(
            @Parameter(description = "Book ID") @PathVariable Long id,
            @RequestBody Set<Long> categoryIds) {
        log.info("Removing categories from book ID: {}", id);
        BookResponse response = bookService.removeCategoriesFromBook(id, categoryIds);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/featured")
    @Operation(summary = "Get featured books", description = "Retrieves a list of featured/popular books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Featured books retrieved successfully")
    })
    public ResponseEntity<List<BookResponse>> getFeaturedBooks(
            @Parameter(description = "Maximum number of books") @RequestParam(defaultValue = "10") int limit) {
        log.debug("Fetching {} featured books", limit);
        List<BookResponse> response = bookService.getFeaturedBooks(limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest")
    @Operation(summary = "Get latest books", description = "Retrieves a list of recently added books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Latest books retrieved successfully")
    })
    public ResponseEntity<List<BookResponse>> getLatestBooks(
            @Parameter(description = "Maximum number of books") @RequestParam(defaultValue = "10") int limit) {
        log.debug("Fetching {} latest books", limit);
        List<BookResponse> response = bookService.getLatestBooks(limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/isbn/{isbn}")
    @Operation(summary = "Check if book exists by ISBN", description = "Checks if a book with the given ISBN exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check completed successfully")
    })
    public ResponseEntity<Boolean> existsByIsbn(
            @Parameter(description = "Book ISBN") @PathVariable String isbn) {
        log.debug("Checking if book exists with ISBN: {}", isbn);
        boolean exists = bookService.existsByIsbn(isbn);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/count")
    @Operation(summary = "Get total book count", description = "Retrieves the total count of active books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully")
    })
    public ResponseEntity<Long> getTotalBookCount() {
        log.debug("Fetching total book count");
        long count = bookService.getTotalBookCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/status/{status}")
    @Operation(summary = "Get book count by status", description = "Retrieves the count of books with a specific status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully")
    })
    public ResponseEntity<Long> getBookCountByStatus(
            @Parameter(description = "Book status") @PathVariable BookStatus status) {
        log.debug("Fetching book count for status: {}", status);
        long count = bookService.getBookCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/category/{categoryId}")
    @Operation(summary = "Get book count by category", description = "Retrieves the count of books in a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully")
    })
    public ResponseEntity<Long> getBookCountByCategory(
            @Parameter(description = "Category ID") @PathVariable Long categoryId) {
        log.debug("Fetching book count for category ID: {}", categoryId);
        long count = bookService.getBookCountByCategory(categoryId);
        return ResponseEntity.ok(count);
    }
}
