package com.yuditsky.classroom.exception;

public class EntityNotFoundException extends ServiceException {
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message, Object... args) {
        super(message, args);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
