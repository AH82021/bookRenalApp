package com.bookstore.inventory.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id", nullable = false, unique = true)
    private Long bookId;

    @Column(name = "book_isbn")
    private String bookIsbn;

    @Column(name = "book_title", nullable = false)
    private String bookTitle;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    @Column(name = "reserved_copies", nullable = false)
    @Builder.Default
    private Integer reservedCopies = 0;
    @Column(name = "rented_copies", nullable = false)
    @Builder.Default
    private Integer rentedCopies = 0;

    @Column(name = "damaged_copies", nullable = false)
    @Builder.Default
    private Integer damagedCopies = 0;

    @Column(name = "lost_copies", nullable = false)
    @Builder.Default
    private Integer lostCopies = 0;

    @Column(name = "minimum_stock", nullable = false)
    @Builder.Default
    private Integer minimumStock = 1;

    @Column(name = "maximum_stock")
    private Integer maximumStock;

    @Column(name = "reorder_level")
    private Integer reorderLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private InventoryStatus status = InventoryStatus.ACTIVE;

    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "shelf_code")
    private String shelfCode;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    // Business logic methods
    public boolean isAvailable() {
        return status == InventoryStatus.ACTIVE && availableCopies > 0;
    }

    public boolean isLowStock() {
        return availableCopies <= minimumStock;
    }

    public boolean needsReorder() {
        return reorderLevel != null && availableCopies <= reorderLevel;
    }

    public void reserve(Integer quantity) {
        if (availableCopies < quantity) {
            throw new IllegalArgumentException("Not enough copies available for reservation");
        }
        availableCopies -= quantity;
        reservedCopies += quantity;
    }

    public void releaseReservation(Integer quantity) {
        if (reservedCopies < quantity) {
            throw new IllegalArgumentException("Cannot release more than reserved copies");
        }
        reservedCopies -= quantity;
        availableCopies += quantity;
    }

    public void rent(Integer quantity) {
        if (reservedCopies < quantity) {
            throw new IllegalArgumentException("Cannot rent more than reserved copies");
        }
        reservedCopies -= quantity;
        rentedCopies += quantity;
    }

    public void returnRental(Integer quantity) {
        if (rentedCopies < quantity) {
            throw new IllegalArgumentException("Cannot return more than rented copies");
        }
        rentedCopies -= quantity;
        availableCopies += quantity;
    }

    public void markDamaged(Integer quantity) {
        if (availableCopies < quantity) {
            throw new IllegalArgumentException("Not enough available copies to mark as damaged");
        }
        availableCopies -= quantity;
        damagedCopies += quantity;
    }

    public void markLost(Integer quantity) {
        if (rentedCopies >= quantity) {
            rentedCopies -= quantity;
        } else if (availableCopies >= quantity) {
            availableCopies -= quantity;
        } else {
            throw new IllegalArgumentException("Not enough copies to mark as lost");
        }
        lostCopies += quantity;
    }

    public void addStock(Integer quantity) {
        totalCopies += quantity;
        availableCopies += quantity;
    }

    public void removeStock(Integer quantity) {
        if (totalCopies < quantity) {
            throw new IllegalArgumentException("Cannot remove more stock than available");
        }
        if (availableCopies >= quantity) {
            totalCopies -= quantity;
            availableCopies -= quantity;
        } else {
            throw new IllegalArgumentException("Cannot remove stock with insufficient available copies");
        }
    }
}