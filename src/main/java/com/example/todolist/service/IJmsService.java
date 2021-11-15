package com.example.todolist.service;

import com.example.todolist.consumer.entity.Todolist;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import javax.jms.Message;
import javax.jms.Session;

public interface IJmsService {

    void producerQueue(final Todolist todoList);

    void producerTopic(final Todolist todoList);

    void producerConsumer(@Payload final Todolist todolist,
                                 @Headers MessageHeaders headers,
                                 Message message, Session session) throws InterruptedException;


}
