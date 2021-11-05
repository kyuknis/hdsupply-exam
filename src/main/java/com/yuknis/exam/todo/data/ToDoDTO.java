package com.yuknis.exam.todo.data;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ToDoDTO {
    
    /**
     * 
     */
    String title;

    /**
     * 
     */
    Date dueBy;

    /**
     * 
     */
    Date completedAt;
}
