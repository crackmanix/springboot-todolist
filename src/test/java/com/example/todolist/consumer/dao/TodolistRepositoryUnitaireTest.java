package com.example.todolist.consumer.dao;

import com.example.todolist.consumer.entity.Todolist;
import com.example.todolist.infra.exception.TodoListNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TodolistRepositoryUnitaireTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ITodolistRepository todolistRepository;

    @BeforeEach
    public void populateDb(){

        //Given
        Todolist todolist = Todolist.builder()
                .name("todo1")
                .build();

        this.testEntityManager.persist(todolist);

        this.testEntityManager.flush();

    }

    @Test
    public void toGet() throws TodoListNotFoundException{

        Todolist todolist;

        //When
        todolist = this.todolistRepository.findByName("todo1")
                .orElseThrow(() -> new TodoListNotFoundException());

        //Then
        assertThat(todolist.getName(), equalTo("todo1"));

    }

}
