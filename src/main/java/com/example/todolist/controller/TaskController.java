package com.example.todolist.controller;

import com.example.todolist.consumer.entity.Task;
import com.example.todolist.infra.exception.TaskNotFoundException;
import com.example.todolist.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "tasks")
public class TaskController implements HealthIndicator {

    @Autowired
    ITaskService taskService;

    @Override
    public Health health(){
        return Health.up().build();
    }

    @GetMapping(path = "/{title}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> getTaskByTitle(@PathVariable String title) throws TaskNotFoundException {

        log.info("Recherche d'une Task avec le Title:{}", title);

        return ResponseEntity.ok(taskService.getTaskByTitle(title));
    }

}
