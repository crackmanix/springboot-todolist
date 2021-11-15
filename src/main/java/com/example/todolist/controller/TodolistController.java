package com.example.todolist.controller;

import com.example.todolist.consumer.entity.Task;
import com.example.todolist.consumer.entity.Todolist;
import com.example.todolist.infra.exception.TodoListNotFoundException;
import com.example.todolist.service.IJmsService;
import com.example.todolist.service.ITaskService;
import com.example.todolist.service.ITodoListService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "todolists")
public class TodolistController implements HealthIndicator {

    @Autowired
    ITaskService taskService;

    @Autowired
    ITodoListService todoListService;

    @Autowired
    IJmsService jmsService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Override
    public Health health(){
        return Health.up().build();
    }

    @GetMapping(path = "/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todolist> getTodolistByName(@PathVariable String name) throws TodoListNotFoundException {
        return ResponseEntity.ok(todoListService.findByName(name));
    }

    @PutMapping(path = "/{name}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todolist> save(
            @PathVariable String name,
            @RequestBody Todolist todolist){

        Todolist res;
        HttpStatus status;

        log.info("Sauvegarde du todoList:{}", name);

        status = HttpStatus.CREATED;

        if(this.todoListService.exist(name)){
            status = HttpStatus.OK;
        }

        res = this.todoListService.save(todolist);

        return new ResponseEntity<>(res, status);
    }

    @GetMapping(path = "/size")
    public ResponseEntity<Long> countAll(){

//        return ResponseEntity.ok(this.todoListService.count());
        return ResponseEntity.ok(this.todoListService.count());

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

    @GetMapping(path = "/test")
    @CircuitBreaker(name = "mainService", fallbackMethod = "testFallBack")
    public ResponseEntity<String> test(){

        log.info("Je suis le service");

        return ResponseEntity.ok("Test OK");

    }

    @GetMapping(path = "/testFall")
    @CircuitBreaker(name = "mainService", fallbackMethod = "testFallBack")
    public ResponseEntity<String> testFall() throws Exception{

        log.info("call testFall");

        throw new Exception("testFall KO");

//        return ResponseEntity.ok("testFall OK");

    }

    @GetMapping(path = "/testFall1")
    @CircuitBreaker(name = "mainService", fallbackMethod = "testFallBack")
    public ResponseEntity<String> testFall1() throws TodoListNotFoundException{

        log.info("call testFall1");

        throw new TodoListNotFoundException();

//        return ResponseEntity.ok("testFall OK");

    }

    private ResponseEntity<String> testFallBack(Exception e) throws Exception{

        return new ResponseEntity<String>("In fallBack method",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/jms/queue/{name}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveJmsQueue(
            @PathVariable String name,
            @RequestBody Todolist todolist) throws InterruptedException{

        log.info("Call controller {}", name);

        this.jmsService.producerQueue(todolist);

        return ResponseEntity.ok(todolist + " sended");
    }

    @PutMapping(path = "/jms/topic/{name}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveJmsTpoic(
            @PathVariable String name,
            @RequestBody Todolist todolist) throws InterruptedException{

        log.info("Call controller {}", name);

        this.jmsService.producerTopic(todolist);

        return ResponseEntity.ok(todolist + " sended");
    }

    @PostMapping(path = "/batch")
    public ResponseEntity<Set<String>> runBatch() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {

        JobParameters parameters;
        JobExecution jobExecution;

        parameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();

        jobExecution = this.jobLauncher.run(this.job, parameters);

        return ResponseEntity.ok(jobExecution.getStepExecutions()
                .stream()
                .map(stepExecution -> stepExecution.getSummary())
                .collect(Collectors.toSet()));



    }

}
