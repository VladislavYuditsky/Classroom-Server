package com.yuditsky.classroom.exception;

public class InvalidDateTimeFormatException extends ServiceException {
    public InvalidDateTimeFormatException() {
    }

    public InvalidDateTimeFormatException(String message, Object... args) {
        super(message, args);
    }

    public InvalidDateTimeFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateTimeFormatException(Throwable cause) {
        super(cause);
    }
}
