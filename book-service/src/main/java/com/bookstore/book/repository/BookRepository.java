package com.bookstore.book.repository;

import com.bookstore.book.model.Book;
import com.bookstore.book.model.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {
    @Query("SELECT b FROM Book WHERE b.deleted= false")
    Page<Book> findAllActive(Pageable pageable);

    @Query("SELECT b FROM Book WHERE b.id= :id AND b.deleted = false ")
    Optional<Book> findActiveById(@Param("id") Long id);

    Optional<Book>  findByIsbnAndDeletedFalse(String isbn);

    @Query("SELECT b FROM Book WHERE b.deleted = false AND (LOWER(b.title)LIKE LOWER(CONCAT('%',:query,'%')) OR (LOWER(b.author)LIKE LOWER(CONCAT('%',:query,'%')) ")
    Page<Book>  findByTitleOrAuthor(@Param("query") String query, Pageable pageable);

    @Query("SELECT DISTINCT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId AND b.deleted = false")
    Page<Book>  findByCategoryId( @Param("categoryId") Long categoryId , Pageable pageable);

    Page<Book> findByStatusAndDeletedFalse(BookStatus bookStatus , Pageable pageable);


    Page<Book>  findBookByFilters(@Param("title") String title,
                                  @Param("author") String author,
                                  @Param("isbn") String isbn,
                                  @Param("categoryId") Long categoryId,
                                  @Param("status") BookStatus status,
                                  @Param("minPrice") BigDecimal minPrice,
                                  @Param("maxPrice") BigDecimal maxPrice,
                                  @Param("publisher") String publisher,
                                  @Param("language") String language,
                                  Pageable pageable);
}
