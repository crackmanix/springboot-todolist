package com.example.todolist.controller;

import com.example.todolist.TodolistApplication;
import com.example.todolist.consumer.entity.Todolist;
import com.example.todolist.service.ITaskService;
import com.example.todolist.service.ITodoListService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(TodolistController.class)
//@ExtendWith(SpringExtension.class)
public class TodoListControllerIntegrationTest {

//    @Autowired
//    MockMvc mvc;
//
//    @MockBean
//    ITodoListService todoListService;
//
//    @MockBean
//    ITaskService taskService;
//
//    @Test
//    @Disabled("Enelver la sécurité pour le faire fonctionner")
//    public void testGiven() throws Exception {
//
//        //When
//        when(this.todoListService.findByName(any(String.class)))
//                .thenReturn(any(Todolist.class));
//
//        this.mvc.perform(get("/todolists/todo1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name", is("todo1")));
//
//
//    }
}
