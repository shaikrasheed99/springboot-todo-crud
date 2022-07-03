package com.crud.todo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Integer> {

    @Query(value = "SELECT * FROM todos where priority =:priority", nativeQuery = true)
    public List<Todo> findAllByPriority(String priority);
}
