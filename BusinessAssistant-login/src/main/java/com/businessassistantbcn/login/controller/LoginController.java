package com.businessassistantbcn.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class LoginController {


    @Autowired
    public LoginController(){
    }

    @GetMapping("/test")
    public String test() {
        return "Hello from BusinessAssistant Barcelona!!!";
    }
}
