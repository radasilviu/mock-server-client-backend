package com.fortech.mockapp.service;

import org.springframework.stereotype.Service;

@Service
public class AppService {

    public String returnHelloWorld(){
        return "HELLO WORLD";
    }

    public String returnHiddenText(){
        return "Super SECRET text";
    }
}
