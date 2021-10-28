package com.example.todolist.consumer.dao;

import com.example.todolist.consumer.entity.Todolist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TodolistRepository extends JpaRepository<Todolist, Long> {

    Optional<Todolist> findByName(String name);
}
