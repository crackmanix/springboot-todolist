package com.example.todolist.service;

import com.example.todolist.consumer.entity.Todolist;
import com.example.todolist.infra.TodolistProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.Session;
import java.util.concurrent.TimeUnit;

@Data
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JmsServiceImpl implements IJmsService {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    TodolistProperties todolistProperties;


    @Override
    public void producerQueue(final Todolist todolist) {

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######            producerQueue           #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("payload: {}", todolist);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");

        this.jmsTemplate.convertAndSend(this.todolistProperties.getMyQueue(),
                todolist);

//        this.jmsMessagingTemplate.convertSendAndReceive("todolistBox", todolist, );

    }

    @Override
    public void producerTopic(final Todolist todolist) {

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######            ProducerTopic           #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("payload: {}", todolist);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");

        this.jmsTemplate.convertAndSend(new ActiveMQTopic(this.todolistProperties.getMyTopic()),
                todolist);


//        this.jmsMessagingTemplate.convertSendAndReceive("todolistBox", todolist, );

    }

    @Override
    @JmsListener(destination = "${todolist.myotherqueue}")
    public void producerConsumer(@Payload final Todolist todolist,
                                 @Headers MessageHeaders headers,
                                 Message message, Session session) throws InterruptedException {


        TimeUnit.SECONDS.sleep(10);

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######           producerConsumer         #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("headers: {}", headers);
        log.info("message: {}", message);
        log.info("session: {}", session);
        log.info("payload: {}", todolist);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");

    }
}
