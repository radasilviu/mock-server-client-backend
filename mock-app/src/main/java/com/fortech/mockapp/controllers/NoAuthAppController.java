package com.fortech.mockapp.controllers;

import com.fortech.mockapp.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class NoAuthAppController {

    AppService appService;

    @Autowired
    public NoAuthAppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> returnHelloWorld() {
        return ResponseEntity.status(HttpStatus.OK).body(appService.returnHelloWorld());
    }
}
