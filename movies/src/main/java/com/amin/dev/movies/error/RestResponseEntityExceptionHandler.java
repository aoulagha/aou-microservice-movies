package com.amin.dev.movies.error;

import com.amin.dev.movies.error.ErrorMessage;
import com.amin.dev.movies.error.MovieExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MovieExistsException.class)
    public ResponseEntity<ErrorMessage> movieAlreadyExists(MovieExistsException exception,
                                                           WebRequest wRequest) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);

    }

    @ExceptionHandler(MovieValidationException.class)
    public ResponseEntity<ErrorMessage> validationIssueRequest(MovieValidationException exception,
                                                           WebRequest wRequest) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

    }
}
