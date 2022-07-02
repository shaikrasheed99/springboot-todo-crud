package com.crud.todo.controller;

import com.crud.todo.exceptions.TodoAlreadyExistException;
import com.crud.todo.repository.Todo;
import com.crud.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TodoController.class})
public class TodoControllerTest {

    @MockBean
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @Autowired
    private MockMvc mockMvc;

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
    void shouldBeAbleToSaveTodoDetails() throws Exception {
        when(todoService.create(any(Todo.class))).thenReturn(todo);

        String todoJson = new ObjectMapper().writeValueAsString(todo);
        ResultActions result = mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoJson));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(todo.getId()))
                .andDo(print());

        verify(todoService, times(1)).create(any(Todo.class));
    }

    @Test
    void shouldNotBeAbleToSaveTodoDetailsWithExistedTodoId() throws Exception {
        when(todoService.create(any(Todo.class))).thenThrow(new TodoAlreadyExistException("Todo has already existed!"));

        String todoJson = new ObjectMapper().writeValueAsString(todo);
        ResultActions result = mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoJson));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message").value("Todo has already existed!"))
                .andDo(print());

        verify(todoService, times(1)).create(any(Todo.class));
    }
}
