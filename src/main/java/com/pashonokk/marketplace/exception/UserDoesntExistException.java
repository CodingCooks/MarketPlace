package com.pashonokk.marketplace.exception;

import org.springframework.http.HttpStatus;

public class UserDoesntExistException extends GenericDisplayableException{
    public UserDoesntExistException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
