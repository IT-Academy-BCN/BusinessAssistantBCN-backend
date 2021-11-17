package com.businessassistantbcn.opendata.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.businessassistantbcn.opendata")
public class MyConfig {

    // to realize the HTTP requests from within REST CLIENT we will use the class helper RestTemplate which is provided by the Spring Framework

    // this bean will be used to realize the HTTP requests
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
