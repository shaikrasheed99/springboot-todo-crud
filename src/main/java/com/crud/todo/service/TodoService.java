package com.crud.todo.service;

import com.crud.todo.exceptions.EmptyRequestBodyException;
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
        if (isEmpty(todo)) throw new EmptyRequestBodyException("Request body should not be empty!");
        Optional<Todo> existedTodo = todoRepository.findById(todo.getId());
        if (existedTodo.isPresent()) throw new TodoAlreadyExistException("Todo has already existed with this Id!");
        return todoRepository.save(todo);
    }

    public Todo getTodoById(int todoId) {
        return isAvailable(todoId);
    }

    public Todo update(Todo todo, int todoId) {
        isAvailable(todoId);
        return todoRepository.save(todo);
    }

    public boolean deleteTodoById(int todoId) {
        isAvailable(todoId);
        todoRepository.deleteById(todoId);
        return true;
    }

    private Todo isAvailable(int todoId) {
        Optional<Todo> todo = todoRepository.findById(todoId);
        if (todo.isEmpty()) throw new TodoNotFoundException("Todo is not found!");
        return todo.get();
    }

    private boolean isEmpty(Todo todo) {
        return todo.getId() == 0 && todo.getDescription() == null && !todo.getCompleted() && todo.getPriority() == null;
    }
}
