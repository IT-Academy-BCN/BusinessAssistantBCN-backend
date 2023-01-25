package com.businessassistantbcn.opendata.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("barcelona")
public class DistrictsConfig {

    private String[] districts;

    public String[] getDistricts() {
        return districts;
    }

    public void setDistricts(String[] districts) {
        this.districts = districts;
    }
}
