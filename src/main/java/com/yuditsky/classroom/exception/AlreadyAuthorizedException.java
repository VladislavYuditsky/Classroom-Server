package com.yuditsky.classroom.exception;

public class AlreadyAuthorizedException extends ServiceException {
    public AlreadyAuthorizedException() {
    }

    public AlreadyAuthorizedException(String message, Object... args) {
        super(message, args);
    }

    public AlreadyAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyAuthorizedException(Throwable cause) {
        super(cause);
    }
}
