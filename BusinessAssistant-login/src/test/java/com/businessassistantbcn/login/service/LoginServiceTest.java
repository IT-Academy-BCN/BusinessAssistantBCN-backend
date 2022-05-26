package com.businessassistantbcn.login.service;

import com.businessassistantbcn.login.config.SecurityConfig;
import com.businessassistantbcn.login.config.SuperUserConfig;
import com.businessassistantbcn.login.config.TestUserConfig;
import com.businessassistantbcn.login.dto.AuthenticationRequest;
import com.businessassistantbcn.login.dto.UserDto;
import com.businessassistantbcn.login.proxy.HttpProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginServiceTest {

    @Autowired
    private SecurityConfig config;

    @Autowired
    private HttpProxy httpProxy;

    @Autowired
    TestUserConfig testUserConfig;

    @Autowired
    @InjectMocks
    private LoginService loginService = new LoginService(new SuperUserConfig(), new TestUserConfig());

    AuthenticationRequest authenticationRequest;
    UserDto testUser;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        authenticationRequest = new AuthenticationRequest("jvicente@gmail.com", "56589pp05s");
        testUser = new UserDto(testUserConfig.getEmail(), testUserConfig.getRoles());

    }

    @Test
    void generateToken() {
        loginService.authenticate(authenticationRequest);
        assertNotNull(loginService.generateToken());
    }

    @Test
    void invalidCredentialsShouldThrowException() {
        authenticationRequest = new AuthenticationRequest("jvicente2@gmail.com", "56589pp05s");
        assertThrows(BadCredentialsException.class, () -> {
            loginService.authenticate(authenticationRequest);
        });
    }

    @Test
    void loadUser() {
        loginService.authenticate(authenticationRequest);
        assertEquals(loginService.loadUser(authenticationRequest).toString(), Mono.just(testUser).toString());
    }

    @Test
    void testAuthenticateWithAuthenticationRequest(){
        assertNotNull(loginService.authenticate(authenticationRequest));
    }

}