package com.example.todolist.controller;

import com.example.todolist.consumer.entity.Task;
import com.example.todolist.infra.exception.TodoListNotFoundException;
import com.example.todolist.service.ITaskService;
import com.example.todolist.service.ITodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "todolists")
public class TodolistController {

    @Autowired
    ITaskService taskService;

    @Autowired
    ITodoListService todoListService;

    @GetMapping(path = "/{name}")
    public List<Task> getTodolistByName(@PathVariable String name) {
        return taskService.getTasksByTodolistName(name);
    }

    @PutMapping(path = "/{name}/tasks",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> addTask(
            @PathVariable String name,
            @RequestBody Task task) throws TodoListNotFoundException {

        return ResponseEntity.ok(this.todoListService.addTask(name, task));

    }

    @DeleteMapping(path = "/{name}")
    public ResponseEntity<String> delete(
            @PathVariable String name) throws TodoListNotFoundException {

        this.todoListService.delete(name);

        return ResponseEntity.ok("success");

    }

    @DeleteMapping(path = "/{name}/rb")
    public ResponseEntity<String> deleteWithRollBack(
            @PathVariable String name) throws TodoListNotFoundException {

        this.todoListService.deleteWithRollBack(name);

        return ResponseEntity.ok("success");

    }

    @DeleteMapping(path = "/{name}/nRb")
    public ResponseEntity<String> deleteWithNoRollBack(
            @PathVariable String name) throws TodoListNotFoundException {

        this.todoListService.deleteWithNoRollBack(name);

        return ResponseEntity.ok("success");

    }

}
