package com.example.todolist.service;

import com.example.todolist.consumer.entity.Task;
import com.example.todolist.consumer.entity.Todolist;
import com.example.todolist.infra.exception.TodoListNotFoundException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import javax.jms.Message;
import javax.jms.Session;
import javax.validation.Valid;

public interface ITodoListService {

    Task addTask(String name, Task task) throws TodoListNotFoundException;

    void delete(String name) throws TodoListNotFoundException;

    void deleteWithNoRollBack(String name) throws TodoListNotFoundException;

    void deleteWithRollBack(String name) throws TodoListNotFoundException;

    Todolist findByName(String name) throws TodoListNotFoundException;

    Todolist save(@Valid final Todolist todolist);

    boolean exist(String name);

    long count();

    Todolist consumer1(@Payload final Todolist todolist,
                                @Headers MessageHeaders headers,
                                Message message, Session session) throws InterruptedException;

    Todolist consumer2(@Payload final Todolist todolist,
                       @Headers MessageHeaders headers,
                       Message message, Session session) throws InterruptedException;

    Todolist consumer3(@Payload final Todolist todolist,
                       @Headers MessageHeaders headers,
                       Message message, Session session) throws InterruptedException;

    Todolist consumer4(@Payload final Todolist todolist,
                       @Headers MessageHeaders headers,
                       Message message, Session session) throws InterruptedException;


}
