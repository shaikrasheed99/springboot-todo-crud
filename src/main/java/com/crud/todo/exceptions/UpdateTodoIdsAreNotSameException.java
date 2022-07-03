package com.crud.todo.exceptions;

public class UpdateTodoIdsAreNotSameException extends RuntimeException {
    public UpdateTodoIdsAreNotSameException(String message) {
        super(message);
    }
}
