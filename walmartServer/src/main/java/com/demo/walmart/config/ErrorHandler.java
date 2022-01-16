package com.demo.walmart.config;

import com.demo.walmart.config.exception.BadRequestException;
import com.demo.walmart.config.exception.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    private final HttpServletRequest httpServletRequest;

    public ErrorHandler(final HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> handle(GenericException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

