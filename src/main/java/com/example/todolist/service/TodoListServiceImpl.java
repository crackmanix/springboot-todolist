package com.example.todolist.service;

import com.example.todolist.consumer.dao.TaskRepository;
import com.example.todolist.consumer.dao.TodolistRepository;
import com.example.todolist.consumer.entity.Task;
import com.example.todolist.consumer.entity.Todolist;
import com.example.todolist.infra.exception.TodoListNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@Transactional(readOnly = true)
public class TodoListServiceImpl implements ITodoListService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TodolistRepository todolistRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = Exception.class)
    public Task addTask(String title, @Valid Task task) throws TodoListNotFoundException {

        Todolist todolist;

        todolist = todolistRepository.findByName(title)
                .orElseThrow(TodoListNotFoundException::new);

        task.setTodolist(todolist);

        task = this.taskRepository.save(task);

        return task;

    }

    @Override
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

        throw new TodoListNotFoundException();

    }

    @Override
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
}
