package com.yuditsky.classroom.exception;

public class InvalidEntityException extends ValidationException {
    public InvalidEntityException() {
    }

    public InvalidEntityException(String message, Object... args) {
        super(message, args);
    }

    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityException(Throwable cause) {
        super(cause);
    }
}
