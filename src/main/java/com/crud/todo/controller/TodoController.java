package com.crud.todo.controller;

import com.crud.todo.helpers.SuccessResponse;
import com.crud.todo.repository.Todo;
import com.crud.todo.service.TodoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable int id) throws JsonProcessingException {
        Todo todo = todoService.getTodoById(id);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setData(todo);
        String response = successResponse.convertToJson();
        return ResponseEntity.status(OK).body(response);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<?> update(@RequestBody Todo todo, @PathVariable int todoId) throws JsonProcessingException {
        Todo updatedTodo = todoService.update(todo, todoId);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setData(updatedTodo);
        String response = successResponse.convertToJson();
        return ResponseEntity.status(OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable int id) throws JsonProcessingException {
        boolean status = todoService.deleteTodoById(id);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setData(Collections.singletonMap("message", "Delete successfully!"));
        String response = successResponse.convertToJson();
        return ResponseEntity.status(OK).body(response);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<?> getTodosByPriority(@PathVariable String priority) throws JsonProcessingException {
        List<Todo> todos = todoService.getTodosByPriority(priority);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setData(todos);
        String response = successResponse.convertToJson();
        return ResponseEntity.status(OK).body(response);
    }
}
