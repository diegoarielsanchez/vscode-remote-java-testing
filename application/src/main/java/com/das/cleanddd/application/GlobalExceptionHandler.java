package com.das.cleanddd.application;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMainResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorMainResponse errorResponse = new ErrorMainResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

   @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorMainResponse> handleInvalidInputException(InvalidInputException ex) {
        ErrorMainResponse errorResponse = new ErrorMainResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Generic exception handler for unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMainResponse> handleException(Exception ex) {
       ErrorMainResponse errorResponse = new ErrorMainResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
