package com.crud.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SuccessResponse {
    private Object data;

    public SuccessResponse() {
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String convertToJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
