package com.businessassistantbcn.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication //disable to show login // defaultconfig
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //disable to hide login 
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

