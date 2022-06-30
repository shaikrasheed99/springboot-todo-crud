package com.crud.todo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    private Todo todo;

    @BeforeEach
    void init() {
        int id = 1;
        String description = "Sleeping";
        boolean completed = false;
        String priority = "high";
        todo = new Todo(id, description, completed, priority);
    }

    @Test
    void shouldBeAbleToSaveTodoDetails() {
        todoRepository.save(todo);
        Todo addedTodo = todoRepository.findById(1).get();

        assertEquals(addedTodo.getDescription(), todo.getDescription());
    }

    @Test
    void shouldBeAbleToGetTodoById() {
        todoRepository.save(todo);

        Todo todo = todoRepository.findById(1).get();

        assertEquals(todo.getDescription(), "Sleeping");
    }

    @Test
    void shouldBeAbleToUpdateTodoDetails() {
        Todo addedTodo = todoRepository.save(todo);

        addedTodo.setCompleted(true);
        Todo updatedTodo = todoRepository.save(addedTodo);

        assertTrue(updatedTodo.getCompleted());
    }

    @Test
    void shouldBeAbleToDeleteTodoById() {
        Todo savedTodo = todoRepository.save(todo);

        todoRepository.deleteById(savedTodo.getId());

        assertFalse(todoRepository.findById(savedTodo.getId()).isPresent());
    }

    @AfterEach
    void erase() {
        todoRepository.deleteAll();
    }
}
