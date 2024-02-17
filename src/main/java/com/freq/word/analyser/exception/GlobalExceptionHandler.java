package com.freq.word.analyser.exception;

import com.freq.word.analyser.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<Object> handleDataNotFoundException(final InvalidArgumentException fileNotFoundException) {
        return new ResponseEntity<>(new ErrorResponse(fileNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }
}
