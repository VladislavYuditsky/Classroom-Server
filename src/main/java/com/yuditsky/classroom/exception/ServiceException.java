package com.yuditsky.classroom.exception;

import java.text.MessageFormat;

public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
