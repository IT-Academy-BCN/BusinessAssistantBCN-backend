package com.businessassistantbcn.login.controller;

import com.businessassistantbcn.login.dto.AuthenticationRequest;
import com.businessassistantbcn.login.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class LoginControllerTest {

    @Mock
    LoginService loginService;

    @InjectMocks
    LoginController loginController;

    String test;
    String testPost;
    AuthenticationRequest authenticationRequest;
    AuthenticationRequest badAuthenticationRequest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        test = "Hello from BusinessAssistant Barcelona!!!";
        testPost = "Hello from BusinessAssistant Barcelona --- POST!!!";
        authenticationRequest = new AuthenticationRequest("jvicente@gmail.com", "56589pp05s");
        badAuthenticationRequest = new AuthenticationRequest("pepe@test.com", "12345");
    }

    @Test
    void testingTestMethod() {
        assertEquals(loginController.test(), test);
    }

    @Test
    void testingTestPostMethod() {
        assertEquals(loginController.testPost(), testPost);
    }

    @Test
    void createAuthenticationToken() {
        loginService.authenticate(authenticationRequest);
        loginService.generateToken();
        assertNotNull(loginController.createAuthenticationToken(authenticationRequest));
    }

    @Test
    void createAuthenticationToken_withBadCredentials() {
        given(loginService.authenticate(badAuthenticationRequest)).willThrow(new BadCredentialsException("Invalid credentials"));
        assertTrue(loginController.createAuthenticationToken(badAuthenticationRequest).getStatusCode().is4xxClientError());
    }

}