package com.businessassistantbcn.usermanagement.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("limitUsers")
@Getter@Setter
public class LimitUsersDbConfig {

    private boolean enabled;
    private int maxUsers;
}
