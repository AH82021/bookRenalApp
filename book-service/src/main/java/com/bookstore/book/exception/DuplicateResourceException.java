package com.bookstore.book.exception;

public class DuplicateResourceException  extends RuntimeException{
    public DuplicateResourceException (String messaage ){
        super(messaage);
    }
}
