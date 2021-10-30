package com.yuknis.exam.todo;

import java.util.Date;
import java.util.Optional;
import com.yuknis.exam.todo.data.ToDo;
import com.yuknis.exam.todo.data.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(
        method = RequestMethod.GET,
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
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
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
    @RequestMapping(
        method = RequestMethod.POST,
        consumes = "application/json",
        produces = "application/json"
    )
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {

        ToDo entity = toDoRepository.save(toDo);
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
    @RequestMapping(
        method = RequestMethod.PATCH,
        value = "/{id}",
        consumes = "application/json",
        produces = "application/json"
    )
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo toDo) {

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
    @RequestMapping(
        method = RequestMethod.DELETE,
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
    @RequestMapping(
        method = RequestMethod.PATCH,
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
