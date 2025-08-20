package com.bookstore.book.model;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.websocket.ClientEndpoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="books")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Book {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "Title is required")
    @Size (max = 255, message = "Title must not exceed 255 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank (message = "Author is required")
    @Size (max = 255, message = "Author must not exceed 255 characters")
    @Column(nullable = false)
    private String author;

    @Pattern(regexp = "^\\d{10}(\\d{13})?$", message = "ISBN must 10 or 13 digits")
    @Column(unique = true, length = 13)
    private String isbn;

    @Column(columnDefinition = "TEXT")
    private String description;

    @DecimalMin(value = "0.0", message = "Price must be greater than 0")
    @Digits(integer = 8,fraction = 2,message = "Price must be valid monetary amount")
    private BigDecimal price;

    @Column(name = "rental_price", precision = 10, scale = 2)
    private BigDecimal rentalPrice;

    @Column(name = "publication_date")
    private LocalDateTime publicationDate;

    @Size (max = 255, message = "Publisher must not exceed 255 characters")
    private String publisher;

    @Min(value = 1, message = "pages must be at least 1")
    private Integer pages;

    @Size (max = 50, message = "Language must not exceed 50 characters")
    @Builder.Default
    private String language = "English";

    @Column( name = "cover_image_url")
    private String coverImageUrl;

    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_categories",joinColumns = @JoinColumn (name = "book_id"), inverseJoinColumns = @JoinColumn (name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder.Default
    private Boolean deleted = false;

    public void softDelete(){

        this.deleted = true;

    }

    public boolean isAvailable(){


        return !deleted && status == BookStatus.AVAILABLE;
    }
    public void addCategory(Category category){
        categories.add(category);
        category.getBooks().add(this);
    }
    public void removeCategory(Category category){
        categories.remove(category);
        category.getBooks().remove(this);
    }


}

