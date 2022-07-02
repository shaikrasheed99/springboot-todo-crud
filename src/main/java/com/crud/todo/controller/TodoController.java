package com.crud.todo.controller;

import com.crud.todo.SuccessResponse;
import com.crud.todo.repository.Todo;
import com.crud.todo.service.TodoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Todo todo) throws JsonProcessingException {
        Todo createdTodo = todoService.create(todo);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setData(Collections.singletonMap("id", createdTodo.getId()));
        String response = successResponse.convertToJson();
        return ResponseEntity.status(CREATED).body(response);
    }
}
