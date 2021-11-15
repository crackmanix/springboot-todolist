package com.example.todolist.service;

import com.example.todolist.consumer.dao.ITaskRepository;
import com.example.todolist.consumer.dao.ITodolistRepository;
import com.example.todolist.consumer.entity.Task;
import com.example.todolist.consumer.entity.Todolist;
import com.example.todolist.infra.exception.TodoListNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import javax.jms.Message;
import javax.jms.Session;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "todolistCache")
public class TodoListServiceImpl implements ITodoListService {

    @Autowired
    ITaskRepository taskRepository;

    @Autowired
    ITodolistRepository todolistRepository;

    @Override
    @Cacheable(key = "#name")
    public Todolist findByName(String name) throws TodoListNotFoundException {

        log.info("Appel de TodoListServiceImpl.findByName avec name:{}", name);

        return Hibernate.unproxy(this.todolistRepository.findByName(name)
                        .orElseThrow(TodoListNotFoundException::new),
                Todolist.class);

    }

    @Override
    @CachePut(key = "#todolist.name", condition = "#todolist != null")
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = Exception.class)
    public Todolist save(@Valid final Todolist todolist) {

        Todolist todolistPrev;

        todolistPrev = this.todolistRepository.findByName(todolist.getName())
                .orElse(new Todolist());

        todolistPrev.setName(todolist.getName());
        todolistPrev.setSurName(todolist.getSurName());

        return Hibernate.unproxy(this.todolistRepository.save(todolistPrev),
                Todolist.class);

    }


    @Override
    @CachePut(cacheNames = "taskCache", key = "#task.title", condition = "#task != null")
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = Exception.class)
    public Task addTask(String name, @Valid Task task) throws TodoListNotFoundException {

        Todolist todolist;

        todolist = this.todolistRepository.findByName(name)
                .orElseThrow(TodoListNotFoundException::new);

        task.setTodolist(todolist);

        task = this.taskRepository.save(task);

        return Hibernate.unproxy(task, Task.class);

    }

    @Override
    @CacheEvict(key = "#name")
    @RolesAllowed({"ROLE_ADMIN"})
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = Exception.class)
    public void delete(String name) throws TodoListNotFoundException {

        Todolist todolist;

        todolist = this.todolistRepository.findByName(name)
                .orElseThrow(TodoListNotFoundException::new);

        this.taskRepository.findAllByTodolist_Name(name)
                .forEach(taskRepository::delete);

        this.todolistRepository.delete(todolist);


    }

    @Override
    @CacheEvict(key = "#name")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false,
            noRollbackFor = TodoListNotFoundException.class)
    public void deleteWithNoRollBack(String name) throws TodoListNotFoundException {

        Todolist todolist;

        todolist = this.todolistRepository.findByName(name)
                .orElseThrow(TodoListNotFoundException::new);

        this.taskRepository.findAllByTodolist_Name(name)
                .forEach(taskRepository::delete);

        this.todolistRepository.delete(todolist);

        throw new TodoListNotFoundException();

    }

    @Override
    @Secured("ROLE_ADMIN")
    @CacheEvict(key = "#name")
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = TodoListNotFoundException.class)
    public void deleteWithRollBack(String name) throws TodoListNotFoundException {

        Todolist todolist;

        todolist = this.todolistRepository.findByName(name)
                .orElseThrow(TodoListNotFoundException::new);

        this.taskRepository.findAllByTodolist_Name(name)
                .forEach(taskRepository::delete);

        this.todolistRepository.delete(todolist);

        throw new TodoListNotFoundException();

    }

    @Override
    public boolean exist(String name) {

        return this.todolistRepository.existsByName(name);

    }

    @Override
    @Cacheable(key = "#root.methodName")
    public long count() {

        log.info("Appel de TodoListServiceImpl.count");

        return this.todolistRepository.count();
    }


    @Override
    @JmsListener(destination = "${todolist.myqueue}", containerFactory = "myFactory")
    @SendTo("${todolist.myotherqueue}")
    @CachePut(key = "#todolist.name", condition = "#todolist != null")
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = Exception.class)
    public Todolist consumer1(@Payload final Todolist todolist,
                                       @Headers MessageHeaders headers,
                                       Message message, Session session) throws InterruptedException {


        TimeUnit.SECONDS.sleep(10);

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######             Consumer1              #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("headers: {}", headers);
        log.info("message: {}", message);
        log.info("session: {}", session);
        log.info("paylod: {}", todolist);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");

        return this.save(todolist);

    }


    @Override
    @JmsListener(destination = "${todolist.myqueue}", containerFactory = "myFactory")
    @SendTo("${todolist.myotherqueue}")
    @CachePut(key = "#todolist.name", condition = "#todolist != null")
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = Exception.class)
    public Todolist consumer2(@Payload final Todolist todolist,
                                       @Headers MessageHeaders headers,
                                       Message message, Session session) throws InterruptedException {


        TimeUnit.SECONDS.sleep(10);

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######             Consumer2              #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("headers: {}", headers);
        log.info("message: {}", message);
        log.info("session: {}", session);
        log.info("payload: {}", todolist);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");

        return this.save(todolist);

    }

    @Override
    @JmsListener(destination = "${todolist.mytopic}", containerFactory = "myFactory")
    @SendTo("${todolist.myotherqueue}")
    @CachePut(key = "#todolist.name", condition = "#todolist != null")
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = Exception.class)
    public Todolist consumer3(@Payload final Todolist todolist,
                              @Headers MessageHeaders headers,
                              Message message, Session session) throws InterruptedException {


        TimeUnit.SECONDS.sleep(10);

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######             Consumer3              #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("headers: {}", headers);
        log.info("message: {}", message);
        log.info("session: {}", session);
        log.info("paylod: {}", todolist);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");

        return this.save(todolist);

    }


    @Override
    @JmsListener(destination = "${todolist.mytopic}", containerFactory = "myFactory")
    @SendTo("${todolist.myotherqueue}")
    @CachePut(key = "#todolist.name", condition = "#todolist != null")
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = Exception.class)
    public Todolist consumer4(@Payload final Todolist todolist,
                              @Headers MessageHeaders headers,
                              Message message, Session session) throws InterruptedException {


        TimeUnit.SECONDS.sleep(10);

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######             Consumer4              #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("headers: {}", headers);
        log.info("message: {}", message);
        log.info("session: {}", session);
        log.info("payload: {}", todolist);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");

        return this.save(todolist);

    }
}
