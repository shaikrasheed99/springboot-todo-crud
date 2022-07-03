package com.crud.todo.helpers;

import com.crud.todo.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class TodoExceptionHandler {

    @ExceptionHandler(value = {
            TodoAlreadyExistException.class,
    })
    public ResponseEntity<?> handleTodoAlreadyExistException(TodoAlreadyExistException todoAlreadyExistException) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(Collections.singletonMap("message", todoAlreadyExistException.getMessage()));
        String response = errorResponse.convertToJson();
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {
            EmptyRequestBodyException.class,
            UpdateTodoIdsAreNotSameException.class
    })
    public ResponseEntity<?> handleEmptyRequestBodyException(Exception exception) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(Collections.singletonMap("message", exception.getMessage()));
        String response = errorResponse.convertToJson();
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {
            InvalidRequestBodyException.class,
    })
    public ResponseEntity<?> handleInvalidRequestBodyException(InvalidRequestBodyException invalidRequestBodyException) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(invalidRequestBodyException.getErrors());
        String response = errorResponse.convertToJson();
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {
            TodoNotFoundException.class,
    })
    public ResponseEntity<?> handleTodoAlreadyExistException(TodoNotFoundException todoNotFoundException) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(Collections.singletonMap("message", todoNotFoundException.getMessage()));
        String response = errorResponse.convertToJson();
        return ResponseEntity.status(NOT_FOUND).body(response);
    }
}
