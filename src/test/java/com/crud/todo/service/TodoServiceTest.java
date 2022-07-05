package com.crud.todo.service;

import com.crud.todo.exceptions.*;
import com.crud.todo.repository.Todo;
import com.crud.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
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
    void shouldBeAbleToThrowEmptyRequestBodyErrorMessageWhenTodoIsEmpty() {
        Todo todo = new Todo();

        assertThrows(EmptyRequestBodyException.class, () -> todoService.create(todo));
    }

    @Test
    void shouldBeAbleToThrowInvalidRequestBodyErrorMessageWhenFieldsAreEmpty() {
        Todo todo = new Todo();
        todo.setDescription("Sleeping");

        assertThrows(InvalidRequestBodyException.class, () -> todoService.create(todo));
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
    void shouldBeAbleToGetTodosByPriority() {
        String priority = "high";
        List<Todo> todosWithHighPriority = new ArrayList<>();
        todosWithHighPriority.add(new Todo(1, "Sleeping", false, "high"));
        todosWithHighPriority.add(new Todo(2, "Coding", false, "high"));
        when(todoRepository.findAllByPriority(priority)).thenReturn(todosWithHighPriority);

        List<Todo> newTodosByPriority = todoService.getTodosByPriority(priority);

        assertEquals(newTodosByPriority.size(), todosWithHighPriority.size());
    }

    @Test
    void shouldBeAbleToThrowExceptionWhenTodosAreNotFoundByPriority() {
        String priority = "high";
        when(todoRepository.findAllByPriority(priority)).thenReturn(List.of());

        assertThrows(TodoNotFoundException.class, () -> todoService.getTodosByPriority(priority));
    }

    @Test
    void shouldBeAbleToUpdateTodoDetailsByTodoId() {
        Todo yetToUpdateTodo = new Todo(todo.getId(), todo.getDescription(), todo.getCompleted(), "low");
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.ofNullable(todo));
        when(todoRepository.save(todo)).thenReturn(yetToUpdateTodo);

        Todo updatedTodo = todoService.update(todo, todo.getId());

        assertEquals(updatedTodo.getPriority(), yetToUpdateTodo.getPriority());
    }

    @Test
    void shouldNotBeAbleToUpdateTodoWhenTodoIdIsNotFound() {
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.update(todo, todo.getId()));
    }

    @Test
    void shouldNotBeAbleToUpdateTodoWhenTodoIdOfRequestBodyIsNotEqualsToProvidedTodoId() {
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.ofNullable(todo));
        todo.setId(2);

        assertThrows(UpdateTodoIdsAreNotSameException.class, () -> todoService.update(todo, 1));
    }

    @Test
    void shouldBeAbleToDeleteTodoByTodoId() {
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.ofNullable(todo));
        doNothing().when(todoRepository).deleteById(this.todo.getId());

        boolean isDeleted = todoService.deleteTodoById(todo.getId());

        assertTrue(isDeleted);
    }

    @Test
    void shouldNotBeAbleToDeleteTodoWhenTodoIsNotFound() {
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.deleteTodoById(todo.getId()));
    }
}
