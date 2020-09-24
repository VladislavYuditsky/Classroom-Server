package com.yuditsky.classroom.exception;

public class AlreadyExistedException extends ServiceException {
    public AlreadyExistedException() {
    }

    public AlreadyExistedException(String message, Object... args) {
        super(message, args);
    }

    public AlreadyExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistedException(Throwable cause) {
        super(cause);
    }
}
