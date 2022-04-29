package com.businessassistantbcn.usermanagement.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import com.businessassistantbcn.usermanagement.service.UserManagementService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/usermanagement")
public class UserManagementController {

    private static final Logger log = LoggerFactory.getLogger(UserManagementController.class);

    @Autowired
    UserManagementService userService;

    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test() {
        return "Hello from BusinessAssistant User!!!";
    }


    @GetMapping("/userUuid/")
    @ApiOperation("Get user by id")
    @ApiResponses (value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden", response = String.class)
    })
    public Mono<?> getUserByUuid(@RequestBody UserUuidDto userUuidDto) {

        Mono<UserDto> userDto = userService.getUserByUuid(userUuidDto.getUuid());

        //Test this.
        return userDto.map(u->ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
              //.defaultIfEmpty(ResponseEntity.noContent().build());
    }

    //Error bad request está bien?
    @PostMapping("/user/")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    public Mono<?> createUser(@RequestBody UserEmailDto userEmailDto) {

        //Tiene que ser mono para utilizar .map por qué?
        Mono<UserDto> userDto = userService.addUser(Mono.just(userEmailDto));

        return userDto.map(u->ResponseEntity.ok(u));
               // .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
              //.defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @GetMapping("/user/")
    @ApiOperation("Get user by email")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden", response = String.class)
    })
    public Mono<?> getUserByEmail(@RequestBody UserEmailDto userEmailDto) {

        Mono<UserDto> userDto = userService.getUserByEmail(userEmailDto.getEmail());

        return userDto.map(u->ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
              //.defaultIfEmpty(ResponseEntity.noContent().build());

    }
}
