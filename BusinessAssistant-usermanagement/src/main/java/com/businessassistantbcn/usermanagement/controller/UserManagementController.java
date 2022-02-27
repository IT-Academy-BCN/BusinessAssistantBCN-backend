package com.businessassistantbcn.usermanagement.controller;

import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/usermanagement")
public class UserManagementController {

    @Autowired
    UserManagementRepository userManagementRepository;

    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test() {
        return "Hello from BusinessAssistant User!!!";
    }

    @GetMapping(value="/users")
    @ApiOperation("Get users")
    @ApiResponse(code = 200, message = "OK")
    public List getUsers(){
        return userManagementRepository.findAll();
    }
}
