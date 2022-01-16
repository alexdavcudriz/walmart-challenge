package com.demo.walmart.config.exception;

public abstract class GenericException extends RuntimeException {

    public GenericException(String message) {
        super(message);
    }
}
