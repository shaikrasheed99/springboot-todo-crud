package com.crud.todo.service;

import com.crud.todo.exceptions.TodoAlreadyExistException;
import com.crud.todo.exceptions.TodoNotFoundException;
import com.crud.todo.repository.Todo;
import com.crud.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo todo;

    @BeforeEach
    public void init() {
        int id = 1;
        String description = "Sleeping";
        boolean completed = false;
        String priority = "high";
        todo = new Todo(id, description, completed, priority);
    }

    @Test
    void shouldBeAbleToSaveTodoDetails() {
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.empty());
        when(todoRepository.save(todo)).thenReturn(todo);

        Todo createdTodo = todoService.create(todo);

        assertEquals(createdTodo.getDescription(), todo.getDescription());
    }

    @Test
    void shouldNotBeAbleToSaveTodoWithSameTodoId() {
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.ofNullable(todo));

        assertThrows(TodoAlreadyExistException.class, () -> todoService.create(todo));
    }

    @Test
    void shouldBeAbleToGetTodoDetailsByTodoId() {
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.ofNullable(todo));

        Todo todoById = todoService.getTodoById(todo.getId());

        assertEquals(todoById.getDescription(), todo.getDescription());
    }

    @Test
    void shouldBeAbleToThrowExceptionWhenTodoIsNotFound() {
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.getTodoById(todo.getId()));
    }

    @Test
    void shouldBeAbleToUpdateTodoDetailsByTodoId() {
        Todo yetToUpdateTodo = new Todo(todo.getId(), todo.getDescription(), todo.getCompleted(), "low");
        when(todoRepository.save(todo)).thenReturn(yetToUpdateTodo);

        Todo updatedTodo = todoService.update(todo, todo.getId());

        assertEquals(updatedTodo.getPriority(), yetToUpdateTodo.getPriority());
    }
}
