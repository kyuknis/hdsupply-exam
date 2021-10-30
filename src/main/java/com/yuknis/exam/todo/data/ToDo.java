package com.yuknis.exam.todo.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * 
 */
@Data
@Entity
@Table(name = "todos")
public class ToDo {

    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "todo_id")
    Long id;

    /**
     * 
     */
    @Column(name = "title")
    String title;

    /**
     * 
     */
    @Column(name = "created_on")
    Date createdOn;

    /**
     * 
     */
    @Column(name = "due_by")
    Date dueBy;

    /**
     * 
     */
    @Column(name = "completed_at")
    Date completedAt;

    
}
