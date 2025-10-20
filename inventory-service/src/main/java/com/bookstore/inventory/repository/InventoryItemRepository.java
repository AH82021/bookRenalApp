package com.bookstore.inventory.repository;

import com.bookstore.inventory.model.InventoryItem;
import com.bookstore.inventory.model.InventoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    Optional<InventoryItem> findByBookId(Long bookId);

    Optional<InventoryItem> findByBookIsbn(String isbn);

    List<InventoryItem> findByStatus(InventoryStatus status);

    Page<InventoryItem> findByStatus(InventoryStatus status, Pageable pageable);

    @Query("SELECT i FROM InventoryItem i WHERE i.availableCopies <= i.minimumStock")
    List<InventoryItem> findLowStockItems();

    @Query("SELECT i FROM InventoryItem i WHERE i.availableCopies <= i.minimumStock")
    Page<InventoryItem> findLowStockItems(Pageable pageable);

    @Query("SELECT i FROM InventoryItem i WHERE i.reorderLevel IS NOT NULL AND i.availableCopies <= i.reorderLevel")
    List<InventoryItem> findItemsNeedingReorder();

    @Query("SELECT i FROM InventoryItem i WHERE i.reorderLevel IS NOT NULL AND i.availableCopies <= i.reorderLevel")
    Page<InventoryItem> findItemsNeedingReorder(Pageable pageable);

    @Query("SELECT i FROM InventoryItem i WHERE i.availableCopies > 0 AND i.status = 'ACTIVE'")
    List<InventoryItem> findAvailableItems();

    @Query("SELECT i FROM InventoryItem i WHERE i.availableCopies > 0 AND i.status = 'ACTIVE'")
    Page<InventoryItem> findAvailableItems(Pageable pageable);

    @Query("SELECT i FROM InventoryItem i WHERE i.availableCopies = 0 AND i.status = 'ACTIVE'")
    List<InventoryItem> findOutOfStockItems();

    @Query("SELECT i FROM InventoryItem i WHERE i.availableCopies = 0 AND i.status = 'ACTIVE'")
    Page<InventoryItem> findOutOfStockItems(Pageable pageable);

    @Query("SELECT i FROM InventoryItem i WHERE " +
            "(:bookTitle IS NULL OR LOWER(i.bookTitle) LIKE LOWER(CONCAT('%', :bookTitle, '%'))) AND " +
            "(:bookAuthor IS NULL OR LOWER(i.bookAuthor) LIKE LOWER(CONCAT('%', :bookAuthor, '%'))) AND " +
            "(:isbn IS NULL OR i.bookIsbn = :isbn) AND " +
            "(:status IS NULL OR i.status = :status) AND " +
            "(:locationCode IS NULL OR i.locationCode = :locationCode)")
    Page<InventoryItem> searchInventoryItems(@Param("bookTitle") String bookTitle,
                                             @Param("bookAuthor") String bookAuthor,
                                             @Param("isbn") String isbn,
                                             @Param("status") InventoryStatus status,
                                             @Param("locationCode") String locationCode,
                                             Pageable pageable);

    @Query("SELECT COUNT(i) FROM InventoryItem i WHERE i.status = 'ACTIVE'")
    long countActiveItems();

    @Query("SELECT COUNT(i) FROM InventoryItem i WHERE i.availableCopies > 0 AND i.status = 'ACTIVE'")
    long countAvailableItems();

    @Query("SELECT COUNT(i) FROM InventoryItem i WHERE i.availableCopies = 0 AND i.status = 'ACTIVE'")
    long countOutOfStockItems();

    @Query("SELECT COUNT(i) FROM InventoryItem i WHERE i.availableCopies <= i.minimumStock")
    long countLowStockItems();

    @Query("SELECT SUM(i.totalCopies) FROM InventoryItem i WHERE i.status = 'ACTIVE'")
    Long getTotalCopiesCount();

    @Query("SELECT SUM(i.availableCopies) FROM InventoryItem i WHERE i.status = 'ACTIVE'")
    Long getAvailableCopiesCount();

    @Query("SELECT SUM(i.rentedCopies) FROM InventoryItem i WHERE i.status = 'ACTIVE'")
    Long getRentedCopiesCount();

    boolean existsByBookId(Long bookId);

    boolean existsByBookIsbn(String isbn);
}
