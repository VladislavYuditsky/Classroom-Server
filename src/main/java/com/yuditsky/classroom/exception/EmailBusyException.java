package com.yuditsky.classroom.exception;

public class EmailBusyException extends ServiceException {
    public EmailBusyException() {
    }

    public EmailBusyException(String message, Object... args) {
        super(message, args);
    }

    public EmailBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailBusyException(Throwable cause) {
        super(cause);
    }
}
