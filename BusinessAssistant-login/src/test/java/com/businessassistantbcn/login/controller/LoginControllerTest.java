package com.businessassistantbcn.login.controller;

import com.businessassistantbcn.login.dto.AuthenticationRequest;
import com.businessassistantbcn.login.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginControllerTest {

    @Mock
    LoginService loginService;

    @InjectMocks
    LoginController loginController;

    String test;
    AuthenticationRequest authenticationRequest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        test = "Hello from BusinessAssistant Barcelona!!!";
        authenticationRequest = new AuthenticationRequest("jvicente@gmail.com", "56589pp05s");
    }

    @Test
    void testingTestMethod() {
        assertEquals(loginController.test(), test);
    }

    @Test
    void createAuthenticationToken() throws Exception {
        loginService.authenticate(authenticationRequest);
        loginService.generateToken();
        assertNotNull(loginController.createAuthenticationToken(authenticationRequest));
    }

}