package com.example.todolist.consumer.dao;

import com.example.todolist.consumer.entity.Task;
import com.example.todolist.consumer.entity.Todolist;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TaskRepository extends JpaRepository<Task, Long> {


    Optional<Task> findByTitle(String title);

    List<Task> findAllByTodolist(Todolist todolist);

    List<Task> findAllByTodolist_Name(String name);

    List<Task> findAllByTodolist_NameAndCompletedIsTrue(String name);

    List<Task> findAllByTodolist_NameAndCompleted(String name, Boolean completed);

    @Query("select t " +
            "from Task t " +
            "where t.completed = :isCompleted " +
            "and (t.title like 'a%' or t.description like '%b%')" +
            "and t.todolist.id = :idTodolist")
    List<Task> findOfDeath(@Param(value = "idTodolist") Long idTodolist,
                        @Param(value = "isCompleted") Boolean isCompleted);
}
