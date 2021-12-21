package com.businessassistantbcn.login.controller;

import com.businessassistantbcn.login.config.LoggerConfig;

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
    	LoggerConfig.log.info("** Saludos desde el logger **");
        return "Hello from BusinessAssistant Barcelona!!!";
    }
    
    @PostMapping("/login")
    public String test2() {
        return "Login functional";
    }
    
}
