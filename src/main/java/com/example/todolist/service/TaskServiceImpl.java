package com.example.todolist.service;

import com.example.todolist.consumer.dao.ITaskRepository;
import com.example.todolist.consumer.entity.Task;
import com.example.todolist.infra.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "taskCache")
public class TaskServiceImpl implements ITaskService{

    @Autowired
    ITaskRepository ITaskRepository;

    @Override
    @Cacheable(key = "#title")
    public Task getTaskByTitle(String title) throws TaskNotFoundException {

        log.info("Début recherche avec le Title ={}", title);

        return Hibernate.unproxy(ITaskRepository.findByTitle(title).orElseThrow(TaskNotFoundException::new),
                Task.class);
    }

    @Override
    @Cacheable(key = "#name")
    public List<Task> getTasksByTodolistName(String name) {
        return ITaskRepository.findAllByTodolist_Name(name).stream()
                .map(task -> Hibernate.unproxy(task, Task.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public long countAll(){
        return this.ITaskRepository.count();
    }

}
