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
    private LoginService loginService = new LoginService(new PropertiesConfig());

    AuthenticationRequest authenticationRequestTest;
    AuthenticationRequest authenticationRequest;
    UserDto testUser;
    UserDto superUser;

    @BeforeEach
    void setUp(){
        // MockitoAnnotations.openMocks(this);

        // Created superUser for testing when userManagement service is ready and connected to login.
        superUser = new UserDto(config.getEmail(), config.getRoles());
        authenticationRequest = new AuthenticationRequest(config.getEmail(), config.getPassword());
    }

    @Test
    void generateToken() {
        loginService.authenticate(authenticationRequest);
        assertNotNull(loginService.generateToken());
    }


    //We expect a BadCredentialsException
    @Test
    void invalidCredentialsShouldThrowException() {
        AuthenticationRequest badAuthenticationRequest = new AuthenticationRequest("ueberch@zarathustra.com", "lJU!LUBOqa7k");
        assertThrows(BadCredentialsException.class, () -> loginService.authenticate(badAuthenticationRequest));
    }

    @Test
    void loadUser() {
        loginService.authenticate(authenticationRequest);
        assertEquals(loginService.loadUser(authenticationRequest).toString(), Mono.just(superUser).toString());
    }

    @Test
    void testAuthenticateWithAuthenticationRequest(){
        assertNotNull(loginService.authenticate(authenticationRequest));
    }
}