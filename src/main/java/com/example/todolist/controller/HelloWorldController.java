package com.example.todolist.controller;

import com.example.todolist.service.IHWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloWorldController {

    @Autowired
    IHWService hwService;

    @GetMapping(path = "/")
    public String getHello() {
        return "Hello World ! "
                .concat(this.hwService.getName())
                .concat(" ")
                .concat(this.hwService.getColor());
    }


}
