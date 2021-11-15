package com.example.todolist.batch;

import com.example.todolist.consumer.entity.Todolist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@Component
public class TodolistItemProcessor implements ItemProcessor<Todolist, Todolist> {

    @Autowired
    Validator validator;

    @Override
    public Todolist process(Todolist todolist) throws Exception {

        Set<ConstraintViolation<Todolist>> violations;

        todolist.setSurName(todolist.getSurName().toUpperCase());

        violations = this.validator.validate(todolist);

        if(!violations.isEmpty()){
            log.error("Impossible de sauvegarder {}", todolist);
            throw new Exception("");
        }

        return todolist;
    }
}
