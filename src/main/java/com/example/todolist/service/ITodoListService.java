package com.example.todolist.service;

import com.example.todolist.consumer.entity.Task;
import com.example.todolist.infra.exception.TodoListNotFoundException;

public interface ITodoListService {

    Task addTask(String name, Task task) throws TodoListNotFoundException;

    void delete(String name) throws TodoListNotFoundException;

    void deleteWithNoRollBack(String name) throws TodoListNotFoundException;

    void deleteWithRollBack(String name) throws TodoListNotFoundException;
}
