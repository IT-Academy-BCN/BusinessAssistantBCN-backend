package com.businessassistantbcn.opendata.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class PropertiesConfig {

    @Bean
    @ConfigurationProperties("url")
    @Qualifier("urlConfig")
    public ClientProperties urlConfig() {
        return new ClientProperties();
    }

    @Bean
    @ConfigurationProperties("security.datasource")
    @Qualifier("securityConfig")
    public ClientProperties securityConfig() {
        return new ClientProperties();
    }

}