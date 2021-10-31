package com.yuknis.exam.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import com.yuknis.exam.todo.data.ToDo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ToDoControllerTests {

    @LocalServerPort
    private int portNumber;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private ToDoController toDoController;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(this.toDoController);
    }

    @Test
    public void getToDoShouldReturnSingleToDo() {

        ToDo controlToDo = new ToDo();
        String title = "Title";
        Date date = new Date();

        controlToDo.setTitle(title);
        controlToDo.setCreatedOn(date);
        controlToDo.setDueBy(date);
        controlToDo.setCompletedAt(date);
        
        String url = "http://localhost:" + portNumber + "/api/v1/todo";
        ResponseEntity<ToDo> createdToDo = this.restTemplate.postForEntity(url, controlToDo, ToDo.class, controlToDo);

        url = url + "/" + createdToDo.getBody().getId();
        ResponseEntity<ToDo> requestResponse = this.restTemplate.getForEntity(url, ToDo.class);

        assertEquals(createdToDo.getBody().getId(), requestResponse.getBody().getId());
        assertEquals(createdToDo.getBody().getTitle(), requestResponse.getBody().getTitle());
        assertEquals(createdToDo.getBody().getCreatedOn(), requestResponse.getBody().getCreatedOn());
        assertEquals(createdToDo.getBody().getDueBy(), requestResponse.getBody().getDueBy());
        assertEquals(createdToDo.getBody().getCompletedAt(),  requestResponse.getBody().getCompletedAt());

    }

    @Test
    public void getToDoShouldReturnNotFoundWhenEntityNotFound() {
        
        String url = "http://localhost:" + portNumber + "/api/v1/todo/" + 99999;
        ResponseEntity<ToDo> requestResult = this.restTemplate.getForEntity(url, ToDo.class);

        assertEquals(HttpStatus.NOT_FOUND, requestResult.getStatusCode());

    }

    @Test
    public void postToDoShouldReturnSavedToDo() {

        ToDo controlToDo = new ToDo();
        String title = "Title";
        Date date = new Date();

        controlToDo.setTitle(title);
        controlToDo.setCreatedOn(date);
        controlToDo.setDueBy(date);
        controlToDo.setCompletedAt(date);
        
        String url = "http://localhost:" + portNumber + "/api/v1/todo";
        ResponseEntity<ToDo> requestResult = this.restTemplate.postForEntity(url, controlToDo, ToDo.class, controlToDo);

        assertNotNull(requestResult.getBody().getId());
        assertEquals(title, requestResult.getBody().getTitle());
        assertEquals(date, requestResult.getBody().getCreatedOn());
        assertEquals(date, requestResult.getBody().getDueBy());
        assertEquals(date, requestResult.getBody().getCompletedAt());

    }

}
