package com.crud.todo.exceptions;

public class EmptyRequestBodyException extends RuntimeException {
    public EmptyRequestBodyException(String message) {
        super(message);
    }
}
