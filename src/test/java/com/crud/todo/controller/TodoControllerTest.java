package com.crud.todo.controller;

import com.crud.todo.exceptions.EmptyRequestBodyException;
import com.crud.todo.exceptions.InvalidRequestBodyException;
import com.crud.todo.exceptions.TodoAlreadyExistException;
import com.crud.todo.exceptions.TodoNotFoundException;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void shouldBeAbleToReturnEmptyRequestBodyErrorMessageWhenRequestBodyIsEmpty() throws Exception {
        Todo todo = new Todo();
        when(todoService.create(any(Todo.class))).thenThrow(new EmptyRequestBodyException("Request body should not be empty!"));

        String todoJson = new ObjectMapper().writeValueAsString(todo);
        ResultActions result = mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoJson));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message").value("Request body should not be empty!"))
                .andDo(print());

        verify(todoService, times(1)).create(any(Todo.class));
    }

    @Test
    void shouldBeAbleToReturnInvalidRequestBodyResponseWhenFieldsAreMissingInRequestBody() throws Exception {
        Todo todo = new Todo();
        todo.setCompleted(false);
        List<Object> errors = new ArrayList<>();
        errors.add(Collections.singletonMap("message", "Todo Id should not be empty!"));
        errors.add(Collections.singletonMap("message", "Todo description should not be empty!"));
        errors.add(Collections.singletonMap("message", "Todo priority should not be empty!"));
        when(todoService.create(any(Todo.class))).thenThrow(new InvalidRequestBodyException(errors));

        String todoJson = new ObjectMapper().writeValueAsString(todo);
        ResultActions result = mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoJson));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error[0].message").value("Todo Id should not be empty!"))
                .andExpect(jsonPath("$.error[1].message").value("Todo description should not be empty!"))
                .andExpect(jsonPath("$.error[2].message").value("Todo priority should not be empty!"))
                .andDo(print());
    }

    @Test
    void shouldBeAbleToGetTodoDetailsByTodoId() throws Exception {
        when(todoService.getTodoById(1)).thenReturn(todo);

        ResultActions result = mockMvc.perform(get("/todo/{id}", 1));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.description").value("Sleeping"))
                .andExpect(jsonPath("$.data.completed").value("false"))
                .andExpect(jsonPath("$.data.priority").value("high"))
                .andDo(print());

        verify(todoService, times(1)).getTodoById(1);
    }

    @Test
    void shouldBeAbleToReturnTodoNotFoundErrorMessageWhenTodoIsNotPresentWithId() throws Exception {
        when(todoService.getTodoById(1)).thenThrow(new TodoNotFoundException("Todo is not found!"));

        ResultActions result = mockMvc.perform(get("/todo/{id}", 1));

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.message").value("Todo is not found!"))
                .andDo(print());

        verify(todoService, times(1)).getTodoById(1);
    }
}
