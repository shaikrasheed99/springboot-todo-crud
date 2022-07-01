package com.crud.todo.service;

import com.crud.todo.repository.Todo;
import com.crud.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void shouldBeAbleToSaveTodoDetails() {
        Todo todo = new Todo(1, "Sleeping", false, "high");
        when(todoRepository.save(todo)).thenReturn(todo);

        Todo createdTodo = todoService.create(todo);

        assertEquals(createdTodo.getDescription(), todo.getDescription());
    }
}
