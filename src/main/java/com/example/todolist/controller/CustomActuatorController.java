package com.example.todolist.controller;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@RestControllerEndpoint(id =  "todoListEndPoint")
public class CustomActuatorController {

    @GetMapping("/custom")
    public @ResponseBody ResponseEntity customEndPoint(){
        return new ResponseEntity<>("Penser à nous faire remonter vos remarques", HttpStatus.OK);
    }
}
