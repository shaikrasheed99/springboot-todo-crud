package com.crud.todo.service;

import com.crud.todo.exceptions.TodoAlreadyExistException;
import com.crud.todo.exceptions.TodoNotFoundException;
import com.crud.todo.repository.Todo;
import com.crud.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo create(Todo todo) {
        Optional<Todo> existedTodo = todoRepository.findById(todo.getId());
        if (existedTodo.isPresent()) throw new TodoAlreadyExistException("Todo has already existed with this Id!");
        return todoRepository.save(todo);
    }

    public Todo getTodoById(int id) {
        Todo todo;
        try {
            todo = todoRepository.findById(id).get();
        } catch (Exception exception) {
            throw new TodoNotFoundException(exception.getMessage());
        }
        return todo;
    }

    public Todo update(Todo todo, int todoId) {
        Optional<Todo> todoById = todoRepository.findById(todoId);
        if (todoById.isEmpty()) throw new TodoNotFoundException("Todo is not found!");
        return todoRepository.save(todo);
    }

    public boolean deleteTodoById(int todoId) {
        todoRepository.deleteById(todoId);
        return true;
    }
}
