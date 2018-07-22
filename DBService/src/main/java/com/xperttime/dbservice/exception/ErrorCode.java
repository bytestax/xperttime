package com.xperttime.dbservice.exception;

public enum ErrorCode {
    LOGIN_ALREADY_EXISTS(101, "Login handle is already used"),
    EMAIL_ALREADY_EXISTS(102, "Email is already used"),
    ID_NOT_FOUND(103, "ID not found, invalid or account deleted"),
    USER_NOT_FOUND(104, "No user found with this detail");

    private String errorMessage;
    private final Integer errorCode;

    private ErrorCode(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
