package com.businessassistantbcn.usermanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/usermanagement")
public class UserManagementController {


    @GetMapping(value="/test")
    public String test() {
        return "Hello from BusinessAssistant User!!!";
    }
}
