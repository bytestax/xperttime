package com.xperttime.dbservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity handleException(UserNotFound e) {
        // log exception
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorCode.USER_NOT_FOUND);
    }

    @ExceptionHandler(LoginAlreadyExists.class)
    public ResponseEntity handleException(LoginAlreadyExists e) {
        // log exception
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorCode.LOGIN_ALREADY_EXISTS);
    }

    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity handleException(EmailAlreadyExists e) {
        // log exception
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
