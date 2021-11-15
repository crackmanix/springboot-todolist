package com.example.todolist.service;

import com.example.todolist.consumer.dao.ITaskRepository;
import com.example.todolist.consumer.dao.ITodolistRepository;
import com.example.todolist.consumer.entity.Todolist;
import com.example.todolist.infra.exception.TodoListNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(UnitaireTestConfiguration.class)
public class TodoListServiceUnitaireTest {

    @MockBean
    ITodolistRepository todolistRepository;

    @MockBean
    ITaskRepository taskRepository;

    @Autowired
    ITodoListService todoListService;

    @BeforeEach
    void setMockOutput() {

//        doNothing().when(this.hwService).populateDb();

        when(this.todolistRepository.findByName("todo1"))
                .thenReturn(Optional.of(Todolist.builder()
                        .id(1L)
                        .name("todo2")
                        .build()));
    }

    @Test
    @DisplayName("Test Unitaire TodoListService")
    void testGet() throws TodoListNotFoundException {

        Todolist todolist;

        todolist = this.todoListService.findByName("todo1");

        assertNotNull(todolist);
        assertEquals(todolist.getId(), 1L);
        assertNotEquals(todolist.getName(), "todo1");

        verify(this.todolistRepository, only()).findByName("todo1");
        verify(this.taskRepository, times(0)).findAllByTodolist(any(Todolist.class));


    }
}
