package com.example.todolist.service;

import com.example.todolist.consumer.entity.Task;
import com.example.todolist.infra.exception.TaskNotFoundException;

import java.util.List;

public interface ITaskService {

    Task getTaskByTitle(String title) throws TaskNotFoundException;

    List<Task> getTasksByTodolistName(String name);

    long countAll();



}
