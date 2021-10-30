package com.yuknis.exam.todo.data;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 */
public interface ToDoRepository extends CrudRepository<ToDo, Long> {
    
}
