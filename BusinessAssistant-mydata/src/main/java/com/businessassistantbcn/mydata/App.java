package com.businessassistantbcn.mydata;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
//@SpringBootApplication(exclude = SqlInitializationAutoConfiguration.class)
//@EnableDiscoveryClient
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}