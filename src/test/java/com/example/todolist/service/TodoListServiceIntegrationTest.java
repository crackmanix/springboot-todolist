package com.example.todolist.service;

import com.example.todolist.consumer.entity.Todolist;
import com.example.todolist.infra.exception.TodoListNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class TodoListServiceIntegrationTest {

    @Autowired
    IHWService hwService;

    @Autowired
    ITodoListService todoListService;

    @Test
    @DisplayName("Test Intégration TodoListService")
    void testGet() throws TodoListNotFoundException {

        Todolist todolist;

        todolist = this.todoListService.findByName("todo1");

        assertNotNull(todolist);
        assertEquals(todolist.getId(), 1L);
        assertEquals(todolist.getName(), "todo1");

    }
}
