package com.businessassistantbcn.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
    @Autowired
    public LoginController(){
    }
    
    @GetMapping("/test")
    public String test() {
    	log.info("** Saludos desde el logger **");
        return "Hello from BusinessAssistant Barcelona!!!";
    }
    
    @PostMapping("/login")
    public String test2() {
        return "Login functional";
    }
    
}
