package com.bookstore.book.repository;

import com.bookstore.book.model.Book;
import com.bookstore.book.model.BookStatus;
import com.bookstore.book.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    // Basic CRUD operations
    Optional<Book> findByIdAndDeletedFalse(Long id);

    Page<Book> findByDeletedFalse(Pageable pageable);

    Optional<Book> findByIsbnAndDeletedFalse(String isbn);

    boolean existsByIsbnAndDeletedFalse(String isbn);

    // Category-based queries
    Page<Book> findByCategoriesContainingAndDeletedFalse(Category category, Pageable pageable);

    @Query("SELECT DISTINCT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId AND b.deleted = false")
    Page<Book> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    // Status-based queries
    Page<Book> findByStatusAndDeletedFalse(BookStatus status, Pageable pageable);

    // Search functionality
    @Query("SELECT DISTINCT b FROM Book b WHERE b.deleted = false AND " +
            "(:keyword IS NULL OR " +
            " LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            " LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            " LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Book> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Advanced search
    @Query("""
       SELECT DISTINCT b FROM Book b LEFT JOIN b.categories c WHERE b.deleted = false AND
       (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND
       (:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND
       (:isbn IS NULL OR b.isbn = :isbn) AND
       (:categoryId IS NULL OR c.id = :categoryId) AND
       (:status IS NULL OR b.status = :status) AND
       (:minPrice IS NULL OR b.price >= :minPrice) AND
       (:maxPrice IS NULL OR b.price <= :maxPrice) AND
       (:publisher IS NULL OR LOWER(b.publisher) LIKE LOWER(CONCAT('%', :publisher, '%'))) AND
       (:language IS NULL OR LOWER(b.language) = LOWER(:language)) AND
       (:available IS NULL OR
              (:available = true AND b.status = 'AVAILABLE' AND b.availableCopies > 0) OR
              (:available = false))
       """)
    Page<Book> searchBooks(@Param("title") String title,
                           @Param("author") String author,
                           @Param("isbn") String isbn,
                           @Param("categoryId") Long categoryId,
                           @Param("status") BookStatus status,
                           @Param("minPrice") BigDecimal minPrice,
                           @Param("maxPrice") BigDecimal maxPrice,
                           @Param("publisher") String publisher,
                           @Param("language") String language,
                           @Param("available") Boolean available,
                           Pageable pageable);

    // Inventory related
    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.availableCopies <= :threshold")
    Page<Book> findLowStockBooks(@Param("threshold") int threshold, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.availableCopies > 0 AND b.status = 'AVAILABLE'")
    Page<Book> findAvailableBooks(Pageable pageable);

    // Books with categories (for eager loading)
    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.categories WHERE b.deleted = false")
    Page<Book> findAllWithCategories(Pageable pageable);

    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.categories WHERE b.id = :id AND b.deleted = false")
    Optional<Book> findByIdWithCategories(@Param("id") Long id);

    // Specific field searches
    Page<Book> findByAuthorContainingIgnoreCaseAndDeletedFalse(String author, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseAndDeletedFalse(String title, Pageable pageable);

    Page<Book> findByPublisherContainingIgnoreCaseAndDeletedFalse(String publisher, Pageable pageable);

    Page<Book> findByLanguageAndDeletedFalse(String language, Pageable pageable);

    // Count operations
    long countByStatusAndDeletedFalse(BookStatus status);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.deleted = false")
    long countActiveBooks();

    @Query("SELECT COUNT(b) FROM Book b JOIN b.categories c WHERE c.id = :categoryId AND b.deleted = false")
    long countBooksByCategory(@Param("categoryId") Long categoryId);

    // Featured/Popular books
    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.status = 'AVAILABLE' ORDER BY b.availableCopies ASC")
    List<Book> findPopularBooks(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.deleted = false ORDER BY b.createdAt DESC")
    List<Book> findLatestBooks(Pageable pageable);
}
