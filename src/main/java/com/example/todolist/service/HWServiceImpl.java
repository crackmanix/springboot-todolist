package com.example.todolist.service;

import com.example.todolist.consumer.dao.ITaskRepository;
import com.example.todolist.consumer.dao.ITodolistRepository;
import com.example.todolist.consumer.entity.Task;
import com.example.todolist.consumer.entity.Todolist;
import com.example.todolist.infra.TodolistProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class HWServiceImpl implements IHWService {

//    @Value("${todolist.name}")
//    String name;

    @Autowired
    TodolistProperties props;

    @Autowired
    ITodolistRepository ITodolistRepository;

    @Autowired
    ITaskRepository ITaskRepository;

    @Override
    public String getName() {
//        return this.name;
        return props.getName();
    }

    @Override
    public String getColor() {
        return props.getColor();
    }

    @PostConstruct
    public void populateDb() {
        Todolist todolist1 = new Todolist();
        todolist1.setName("todo1");
        todolist1.setSurName("todo1");

        Todolist todolist2 = new Todolist();
        todolist2.setName("todo2");
        todolist2.setSurName("todo2");

        Todolist todolist3 = new Todolist();
        todolist3.setName("todo3");
        todolist3.setSurName("todo3");

        todolist1 = ITodolistRepository.save(todolist1);
        todolist2 = ITodolistRepository.save(todolist2);
        todolist3 = ITodolistRepository.save(todolist3);

        Task task11 = new Task();
        task11.setTitle("aTask11");
        task11.setDescription("description task11");
        task11.setCompleted(false);
        task11.setTodolist(todolist1);

        ITaskRepository.save(task11);

        Task task21 = new Task();
        task21.setTitle("bTask21");
        task21.setDescription("description task21");
        task21.setCompleted(true);
        task21.setTodolist(todolist2);

        Task task22 = new Task();
        task22.setTitle("bTask22");
        task22.setDescription("description btask22");
        task22.setCompleted(false);
        task22.setTodolist(todolist2);

        ITaskRepository.save(task21);
        ITaskRepository.save(task22);

        Task task31 = new Task();
        task31.setTitle("bTask31");
        task31.setDescription("description task31");
        task31.setCompleted(true);
        task31.setTodolist(todolist3);

        Task task32 = new Task();
        task32.setTitle("bTask32");
        task32.setDescription("description btask32");
        task32.setCompleted(false);
        task32.setTodolist(todolist3);

        ITaskRepository.save(task31);
        ITaskRepository.save(task32);
    }
}
