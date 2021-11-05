package com.yuknis.exam.todo;

import java.util.Date;
import java.util.Optional;
import com.yuknis.exam.todo.data.ToDo;
import com.yuknis.exam.todo.data.ToDoDTO;
import com.yuknis.exam.todo.data.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 
 */
@RestController
@RequestMapping(value = "/api/v1/todo")
public class ToDoController {

    @Autowired
    ToDoRepository toDoRepository;

    /**
     * Gets all of the ToDo records
     * 
     * @return
     */
    @ResponseBody
    @GetMapping(
        produces = "application/json"
    )
    public ResponseEntity<Iterable<ToDo>> getAllToDos() {

        Iterable<ToDo> toDos = toDoRepository.findAll();
        return new ResponseEntity<>(toDos, HttpStatus.OK);

    }

    /**
     * Gets only the ToDo record with the id specified
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(
        value = "/{id}",
        produces = "application/json"
    )
    public ResponseEntity<ToDo> getToDo(@PathVariable Long id) {

        Optional<ToDo> entity = toDoRepository.findById(id);
        return entity.isPresent() ? new ResponseEntity<>(entity.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    /**
     * Creates a new ToDo with the request body's contents
     * 
     * @param toDo
     * @return
     */
    @ResponseBody
    @PostMapping(
        consumes = "application/json",
        produces = "application/json"
    )
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDoDTO toDo) {

        ToDo entity = ToDo.builder()
        .title(toDo.getTitle())
        .completedAt(toDo.getCompletedAt())
        .dueBy(toDo.getDueBy())
        .build();

        entity = toDoRepository.save(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);

    }

    /**
     * Updates the todo with the specified id
     * 
     * @param id
     * @param toDo
     * @return
     */
    @ResponseBody
    @PatchMapping(
        value = "/{id}",
        consumes = "application/json",
        produces = "application/json"
    )
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDoDTO toDo) {

        Optional<ToDo> entity = toDoRepository.findById(id);

        if(entity.isPresent()) {
            ToDo retrievedToDo = entity.get();
            retrievedToDo.setTitle(toDo.getTitle());
            retrievedToDo.setDueBy(toDo.getDueBy());
            retrievedToDo.setCompletedAt(toDo.getCompletedAt());
            retrievedToDo = toDoRepository.save(retrievedToDo);
            return new ResponseEntity<>(retrievedToDo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Deletes the todo with the specified id
     * 
     * @param id
     */
    @DeleteMapping(
        value = "/{id}"
    )
    public void deleteToDo(@PathVariable Long id) {
        
        Optional<ToDo> entity = toDoRepository.findById(id);

        if(entity.isPresent()) {
            ToDo toDo = entity.get();
            toDoRepository.delete(toDo);
        }
        
    }

    /**
     * Marks the todo with the specified id as complete
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @PatchMapping(
        value = "/{id}/complete"
    )
    public ResponseEntity<ToDo> completeToDo(@PathVariable Long id) {

        Optional<ToDo> entity = toDoRepository.findById(id);

        if(entity.isPresent()) {
            ToDo toDo = entity.get();
            toDo.setCompletedAt(new Date());
            toDo = toDoRepository.save(toDo);
            return new ResponseEntity<ToDo>(toDo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    
}
