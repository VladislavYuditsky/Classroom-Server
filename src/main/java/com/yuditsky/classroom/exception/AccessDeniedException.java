package com.yuditsky.classroom.exception;

public class AccessDeniedException extends ServiceException {
    public AccessDeniedException() {
    }

    public AccessDeniedException(String message, Object... args) {
        super(message, args);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }
}
