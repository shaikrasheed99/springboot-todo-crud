package com.crud.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorResponse {
    private Object error;

    public ErrorResponse() {
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String convertToJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
