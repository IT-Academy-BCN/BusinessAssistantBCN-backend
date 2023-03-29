package com.businessassistantbcn.login.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Getter
@Setter
public class PropertiesConfig {

    //ProxyConfig
    @Value("${url.connection_timeout}")
    private int connection_timeout;

    //SecurityConfig
    @Value("${security.datasource.signUpUrl}")
    private String signUpUrl;
    @Value("${security.datasource.secret}")
    private String secret;
    @Value("${security.datasource.tokenPrefix}")
    private String tokenPrefix;
    @Value("${security.datasource.expiresIn}")
    private long expiresIn;
    @Value("${security.datasource.authoritiesClaim}")
    private String authoritiesClaim;
    @Value("${security.datasource.err}")
    private String err;
    @Value("${security.datasource.userManagementUrl}")
    private String userManagementUrl;


    //SuperUserConfig
    @Value("${security.superuser.email}")
    private String email;
    @Value("${security.superuser.password}")
    private String password;
    @Value("#{'${security.superuser.roles:}'.split(',')}")
    private List<String> roles;


    //TestUserConfig
    //@Value("${security.testuser.email}")
   // private String emailTest;
    //@Value("${security.testuser.password}")
    //private String passwordTest;
    //@Value("#{'${security.testuser.roles:}'.split(',')}")
    //private List<String> rolesTest;

}


