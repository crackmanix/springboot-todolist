package com.example.todolist.batch;

import com.example.todolist.consumer.entity.Todolist;
import com.example.todolist.service.ITodoListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Component
public class TodolistItemWriter implements ItemWriter<Todolist> {

    @Autowired
    ITodoListService todoListService;

    @PersistenceContext(unitName = "entityManagerFactory")
    EntityManager entityManager;

    @Override
    public void write(List<? extends Todolist> todolists) throws Exception {

        todolists.forEach(todolist -> {
            log.info("Enregistrement en base de {}", todolist);
            this.todoListService.save(todolist);
        });

        this.entityManager.flush();
    }
}
