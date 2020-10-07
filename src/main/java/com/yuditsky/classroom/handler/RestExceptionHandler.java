package com.yuditsky.classroom.handler;

import com.yuditsky.classroom.exception.*;
import com.yuditsky.classroom.model.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Log4j2
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({AlreadyAuthorizedException.class, AccessDeniedException.class})
    public ResponseEntity<ErrorMessage> handle(ServiceException exception) {
        log.error(exception);
        return handleThrowable(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({AlreadyExistedException.class, EmailBusyException.class})
    protected ResponseEntity<ErrorMessage> handleAlreadyExistedException(ServiceException exception) {
        log.error(exception);
        return handleThrowable(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleNotFoundException(ServiceException exception) {
        log.error(exception);
        return handleThrowable(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ValidationException.class, InvalidDateTimeFormatException.class})
    protected ResponseEntity<ErrorMessage> handleValidationException(ServiceException exception) {
        log.error(exception);
        return handleThrowable(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorMessage> handleThrowable(Throwable throwable, HttpStatus httpStatus) {
        ErrorMessage errorMessage = new ErrorMessage(throwable.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, httpStatus);
    }
}