package com.crud.todo.service;

import com.crud.todo.exceptions.*;
import com.crud.todo.repository.Todo;
import com.crud.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo create(Todo todo) {
        List<Object> errors = new ArrayList<>();
        if (isEmpty(todo)) throw new EmptyRequestBodyException("Request body should not be empty!");
        if (todo.getId() == 0) errors.add(Collections.singletonMap("message", "Todo Id should not be empty!"));
        if (todo.getDescription() == null)
            errors.add(Collections.singletonMap("message", "Todo description should not be empty!"));
        if (todo.getPriority() == null)
            errors.add(Collections.singletonMap("message", "Todo priority should not be empty!"));

        if (!errors.isEmpty()) throw new InvalidRequestBodyException(errors);

        Optional<Todo> existedTodo = todoRepository.findById(todo.getId());
        if (existedTodo.isPresent()) throw new TodoAlreadyExistException("Todo has already existed with this Id!");
        return todoRepository.save(todo);
    }

    public Todo getTodoById(int todoId) {
        return isAvailable(todoId);
    }

    public Todo update(Todo todo, int todoId) {
        isAvailable(todoId);
        if (todoId != todo.getId())
            throw new UpdateTodoIdsAreNotSameException("Provided Todo Id and Request Body Todo Id are not same!");
        return todoRepository.save(todo);
    }

    public boolean deleteTodoById(int todoId) {
        isAvailable(todoId);
        todoRepository.deleteById(todoId);
        return true;
    }

    public List<Todo> getTodosByPriority(String priority) {
        List<Todo> todos = todoRepository.findAllByPriority(priority);
        if (todos.isEmpty()) throw new TodoNotFoundException("Todos are not found with this priority!");
        return todos;
    }

    public List<Todo> getTodosByCompletedStatus(boolean completedStatus) {
        return todoRepository.findAllByCompletedStatus(completedStatus);
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
