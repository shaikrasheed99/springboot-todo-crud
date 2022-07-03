package com.crud.todo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Integer> {

    @Query(value = "SELECT * FROM todos where priority =:priority", nativeQuery = true)
    List<Todo> findAllByPriority(String priority);

    @Query(value = "SELECT * FROM todos where completed =:completedStatus", nativeQuery = true)
    List<Todo> findAllByCompletedStatus(boolean completedStatus);
}
