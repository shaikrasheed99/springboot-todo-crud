package com.crud.todo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void shouldBeAbleToSaveTodoDetails() {
        Todo todo = new Todo(1, "Sleep", false, "high");

        todoRepository.save(todo);
        Todo addedTodo = todoRepository.findById(1).get();

        assertEquals(addedTodo.getDescription(), todo.getDescription());
    }
}
