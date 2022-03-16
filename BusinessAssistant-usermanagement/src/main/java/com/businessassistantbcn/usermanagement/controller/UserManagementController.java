package com.businessassistantbcn.usermanagement.controller;

import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.repository.IUserManagementRepository;
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
    IUserManagementRepository iUserManagementRepository;


    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test() {
        return "Hello from BusinessAssistant User!!!";
    }

    @ApiOperation("Get get-users")
    @ApiResponse(code = 200, message = "OK")
    @GetMapping(value="/get-users")
    public List<User> getUser(){
        return iUserManagementRepository.findAll();
    }
}
