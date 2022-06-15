package com.businessassistantbcn.usermanagement.controller;

import com.businessassistantbcn.usermanagement.service.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.util.Map;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/usermanagement")
public class UserManagementController {

    @Autowired
    UserManagementService userManagementService;

    @GetMapping(value="/test")
    public String test() {
        return "Hello from BusinessAssistant User!!!";
    }

    /*@GetMapping(value="/user")
    public String user() {
        return "acció user!!!";
    }*/

    @GetMapping("/user")
    @Operation(summary = "get user")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable") })
    public Mono<?> userResponse(
            @RequestParam(required = false) String email,
            @RequestParam(required = false)  String password,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map);
        //return userManagementService.getUser(email, password);
        return userManagementService.getUserByEmail(email);
    }

    private void validateRequestParameters(Map<String, String> map)
    {
        if (map.keySet().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        for (String key : map.keySet()) {
            if (!key.equals("email") && !key.equals("password")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
