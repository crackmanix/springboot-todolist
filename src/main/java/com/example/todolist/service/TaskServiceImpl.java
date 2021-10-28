package com.example.todolist.service;

import com.example.todolist.consumer.dao.TaskRepository;
import com.example.todolist.consumer.entity.Task;
import com.example.todolist.infra.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements ITaskService{

    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task getTaskByTitle(String title) throws TaskNotFoundException {

        log.info("Début recherche avec le Title ={}", title);

        return taskRepository.findByTitle(title).orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public List<Task> getTasksByTodolistName(String name) {
        return taskRepository.findAllByTodolist_Name(name);
    }

}
