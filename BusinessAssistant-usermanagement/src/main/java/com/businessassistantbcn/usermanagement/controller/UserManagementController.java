package com.businessassistantbcn.usermanagement.controller;

import com.businessassistantbcn.usermanagement.dto.input.SingupDto;
import com.businessassistantbcn.usermanagement.dto.output.UserDto;
import com.businessassistantbcn.usermanagement.service.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/usermanagement")
public class UserManagementController {

    @Autowired
    UserManagementService userManagementService;

    @GetMapping(value="/test")
    public String test() {
        return "Hello from BusinessAssistant User!!!";
    }


    @GetMapping("/user/email")
    //@PreAuthorize("hasAuthority('SUPERUSER')") // Comentar en modo dev
    @Operation(summary = "get user")

    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable") })

    public Mono<?> getUserByEmail(
            @RequestBody UserDto user) {
        return userManagementService.getUserByEmail(user);
    }

    @GetMapping("/user/uuid")
    //@PreAuthorize("hasAuthority('SUPERUSER')") // Comentar en modo dev
    @Operation(summary = "get user")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable") })
    public Mono<?> getUserById(
            @RequestBody UserDto user) {
        return userManagementService.getUserById(user);

    }

    @PostMapping("/user")
    //@PreAuthorize("hasAuthority('SUPERUSER')") // Comentar en modo dev
    @Operation(summary = "add user")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable") })
    public Mono<?> addUser(@RequestBody SingupDto singupDto){
        return userManagementService.addUser(singupDto);
    }

}
