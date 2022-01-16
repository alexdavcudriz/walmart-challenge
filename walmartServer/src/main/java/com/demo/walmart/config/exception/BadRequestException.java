package com.demo.walmart.config.exception;

public class BadRequestException extends GenericException{

    public BadRequestException(String message) {
        super(message);
    }
}
