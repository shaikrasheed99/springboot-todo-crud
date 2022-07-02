package com.crud.todo.exceptions;

import java.util.List;

public class InvalidRequestBodyException extends RuntimeException {
    private final List<Object> errors;

    public InvalidRequestBodyException(List<Object> errors) {
        this.errors = errors;
    }

    public List<Object> getErrors() {
        return errors;
    }
}
