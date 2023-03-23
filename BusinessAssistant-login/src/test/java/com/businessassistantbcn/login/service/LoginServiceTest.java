package com.businessassistantbcn.login.service;

import com.businessassistantbcn.login.config.PropertiesConfig;
import com.businessassistantbcn.login.dto.AuthenticationRequest;
import com.businessassistantbcn.login.dto.UserDto;
import com.businessassistantbcn.login.proxy.HttpProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LoginServiceTest {

    @Autowired
    PropertiesConfig config;

    @Autowired
    HttpProxy httpProxy;


    @Autowired
    @InjectMocks
    private LoginService loginService = new LoginService(new PropertiesConfig(), new PropertiesConfig());

    AuthenticationRequest authenticationRequestTest;
    AuthenticationRequest authenticationRequest;
    UserDto testUser;
    UserDto superUser;

    @BeforeEach
    void setUp(){
        // MockitoAnnotations.openMocks(this);

        authenticationRequestTest = new AuthenticationRequest(config.getEmailTest(), config.getPasswordTest());
        testUser = new UserDto(config.getEmailTest(), config.getRolesTest());
        // Created suyperUser for testing when userManagement service is ready and connected to login.
        superUser = new UserDto(config.getEmail(), config.getRoles());
        authenticationRequest = new AuthenticationRequest(config.getEmail(), config.getPassword());

    }

    @Test
    void generateToken() {
        loginService.authenticate(authenticationRequestTest);
        assertNotNull(loginService.generateToken());
    }

    @Test
    void invalidCredentialsShouldThrowException() {
        AuthenticationRequest badAuthenticationRequest = new AuthenticationRequest("jvicente2@gmail.com", "56589pp05s");
        assertThrows(BadCredentialsException.class, () -> loginService.authenticate(badAuthenticationRequest));
    }

    @Test
    void loadUser() {
        loginService.authenticate(authenticationRequestTest);
        assertEquals(loginService.loadUser(authenticationRequestTest).toString(), Mono.just(testUser).toString());
    }

    @Test
    void testAuthenticateWithAuthenticationRequest(){
        assertNotNull(loginService.authenticate(authenticationRequestTest));
    }

}