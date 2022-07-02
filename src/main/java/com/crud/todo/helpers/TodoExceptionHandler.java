package com.crud.todo.helpers;

import com.crud.todo.exceptions.TodoAlreadyExistException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class TodoExceptionHandler {

    @ExceptionHandler(value = {
            TodoAlreadyExistException.class
    })
    public ResponseEntity<?> handleTodoAlreadyExistException(TodoAlreadyExistException todoAlreadyExistException) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(Collections.singletonMap("message", todoAlreadyExistException.getMessage()));
        String response = errorResponse.convertToJson();
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }
}