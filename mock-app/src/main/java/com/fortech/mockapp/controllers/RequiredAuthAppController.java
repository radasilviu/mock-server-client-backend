package com.fortech.mockapp.controllers;

import com.fortech.mockapp.configuration.model.ResponseMessage;
import com.fortech.mockapp.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
public class RequiredAuthAppController {
    private AppService appService;
    @Autowired
    public RequiredAuthAppController(AppService appService) {
        this.appService = appService;
    }
    @GetMapping("/secret")
    ResponseEntity<ResponseMessage> returnHiddenText(){
        final ResponseMessage responseMessage = new ResponseMessage("Message from Resource server");
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
