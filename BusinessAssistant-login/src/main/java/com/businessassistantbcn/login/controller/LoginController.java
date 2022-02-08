package com.businessassistantbcn.login.controller;

import com.businessassistantbcn.login.dto.AuthenticationRequest;
import com.businessassistantbcn.login.dto.AuthenticationResponse;
import com.businessassistantbcn.login.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/businessassistantbcn/api/v1")
public class LoginController {

    final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginService loginService;

    @Autowired
    public LoginController(){
    }

    @GetMapping("/test")
    public String test() {
        log.info("** Saludos desde el logger **");
        return "Hello from BusinessAssistant Barcelona!!!";
    }


    /**
     * entrada:
     * {
     * 	"email": "jvicente@gmail.com",
     * 	"password": "56589pp05s"
     * }
     *
     * salida:
     * {
     *     token: xxxxxx
     * }
     * @param authenticationRequest
     * @return
     * @throws Exception
     */

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = loginService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = loginService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
