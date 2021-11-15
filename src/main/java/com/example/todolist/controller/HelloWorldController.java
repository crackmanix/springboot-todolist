package com.example.todolist.controller;

import com.example.todolist.service.IHWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloWorldController implements HealthIndicator {

    @Autowired
    IHWService hwService;

    @Override
    public Health health(){
        return Health.up().build();
    }

    @GetMapping(path = "/")
    public String getHello() {
        return "Hello World ! "
                .concat(this.hwService.getName())
                .concat(" ")
                .concat(this.hwService.getColor());
    }


}
