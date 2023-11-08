package com.pashonokk.marketplace.exception;

import org.springframework.http.HttpStatus;

public class CategoryDoesntExistException extends GenericDisplayableException {
    public CategoryDoesntExistException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
