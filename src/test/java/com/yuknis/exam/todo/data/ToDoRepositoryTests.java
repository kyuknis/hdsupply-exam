package com.yuknis.exam.todo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToDoRepositoryTests {
    
    @Autowired
    private ToDoRepository toDoRepository;

    @Test
    public void repositorySaveShouldStoreToDo() {

        String title = "Test";
        Date date = new Date();

        ToDo toDo = new ToDo();
        toDo.setTitle(title);
        toDo.setCompletedAt(date);
        toDo.setDueBy(date);
        toDo = toDoRepository.save(toDo);

        assertNotNull(toDo.getId());
        assertEquals("Test", toDo.getTitle());
        assertEquals(date, toDo.getCompletedAt());
        assertTrue(toDo.getCreatedOn() instanceof Date);
        assertEquals(date, toDo.getDueBy());

    }

    @Test
    public void repositorySaveShouldUpdateToDo() {

        String title = "Test";
        Date date = new Date();

        ToDo toDo = new ToDo();
        toDo.setTitle(title);
        toDo.setCompletedAt(date);
        toDo.setCreatedOn(date);
        toDo.setDueBy(date);
        toDo = toDoRepository.save(toDo);

        title = "The new title";
        Long originalId = toDo.getId();
        toDo.setTitle(title);
        toDo = toDoRepository.save(toDo);

        assertEquals(originalId, toDo.getId());
        assertEquals(title, toDo.getTitle());
        assertEquals(date, toDo.getCompletedAt());
        assertEquals(date, toDo.getCreatedOn());
        assertEquals(date, toDo.getDueBy());

    }

    @Test
    public void repositoryFindShouldFindToDo() {

        String title = "Test";
        Date date = new Date();

        ToDo toDo = new ToDo();
        toDo.setTitle(title);
        toDo.setCompletedAt(date);
        toDo.setCreatedOn(date);
        toDo.setDueBy(date);
        toDo = toDoRepository.save(toDo);

        Long toDoId = toDo.getId();
        ToDo foundToDo = toDoRepository.findById(toDo.getId()).orElseThrow(() -> new IllegalArgumentException(String.format("The provided id of %n was not found", toDoId)));

        assertEquals(toDo.getId(), foundToDo.getId());
        assertEquals(toDo.getTitle(), foundToDo.getTitle());
        assertEquals(toDo.getCompletedAt(), foundToDo.getCompletedAt());
        assertEquals(toDo.getCreatedOn(), foundToDo.getCreatedOn());
        assertEquals(toDo.getDueBy(), foundToDo.getDueBy());

    }

    @AfterEach
    private void resetDatabase() {
        toDoRepository.deleteAll();
    }

}
