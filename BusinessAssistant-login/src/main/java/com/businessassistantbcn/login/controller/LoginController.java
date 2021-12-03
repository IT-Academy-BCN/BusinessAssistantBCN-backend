package com.businessassistantbcn.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
public class LoginController {

    @Autowired
    public LoginController(){
    }

    
    @GetMapping("/test")
    public String test() {
        return "Hello from BusinessAssistant Barcelona!!!";
    }
    
    @PostMapping("/login")
    public String test2() {
        return "Login functional";
    }
    
}
