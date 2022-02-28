package com.businessassistantbcn.usermanagement.controller;

import com.businessassistantbcn.usermanagement.dto.AuthenticationRequest;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/usermanagement")
public class UserManagementController {


    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test() {
        return "Hello from BusinessAssistant User!!!";
    }

    @GetMapping(value="/verify_user")
    @ApiOperation("Verify user credentials")
    @ApiResponse(code = 200, message = "OK")
    public List<String> verifyCredentials(@RequestBody AuthenticationRequest authenticationRequest) {
        //TODO create service that validates user credentials against the DB
        //TODO Users should have a list of roles instead of just one role
        UserDto user = new UserDto("UUID", "random@email.com", "USER");
        return Arrays.asList(user.getRole());
    }


}
