package com.example.todolist.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UnitaireTestConfiguration {

    @Bean
    public TodoListServiceImpl todoListService(){
        return new TodoListServiceImpl();
    }

}
