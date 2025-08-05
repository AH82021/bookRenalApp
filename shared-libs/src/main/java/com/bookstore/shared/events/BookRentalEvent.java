package main.java.com.bookstore.shared.events;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class BookRentalEvent {
    
    private String eventType;
    private Long userId;
    private Long bookId;
    private Long rentalId;
    private String userEmail;
    private String bookTitle;
    private String bookAuthor;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rentalStartDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rentalEndDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventTimestamp;

    // Constructors
    public BookRentalEvent() {
        this.eventTimestamp = LocalDateTime.now();
    }

    public BookRentalEvent(String eventType, Long userId, Long bookId, Long rentalId) {
        this();
        this.eventType = eventType;
        this.userId = userId;
        this.bookId = bookId;
        this.rentalId = rentalId;
    }

    // Getters and Setters
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public LocalDateTime getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDateTime rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDateTime getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(LocalDateTime rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(LocalDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    // Event type constants
    public static class EventTypes {
        public static final String RENTAL_CREATED = "RENTAL_CREATED";
        public static final String RENTAL_RETURNED = "RENTAL_RETURNED";
        public static final String RENTAL_OVERDUE = "RENTAL_OVERDUE";
        public static final String RENTAL_EXTENDED = "RENTAL_EXTENDED";
        public static final String RENTAL_CANCELLED = "RENTAL_CANCELLED";
    }
}
