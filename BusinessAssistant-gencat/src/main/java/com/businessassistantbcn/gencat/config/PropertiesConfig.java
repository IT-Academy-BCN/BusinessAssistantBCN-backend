package com.businessassistantbcn.gencat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("url")
public class PropertiesConfig {

    private Integer connection_timeout;//millis
    private String ds_test;
    private Integer maxBytesInMemory;

    private String ds_economicActivities;

    public int getMaxBytesInMemory() {
        return maxBytesInMemory;
    }

    public void setMaxBytesInMemory(String maxBytesInMemory) {
        this.maxBytesInMemory = Integer.parseInt(maxBytesInMemory);
    }

    public Integer getConnection_timeout() {
        return connection_timeout;
    }

    public void setConnection_timeout(String connection_timeout) {
        this.connection_timeout = Integer.parseInt(connection_timeout);
    }

    public String getDs_test() {
        return ds_test;
    }

    public void setDs_test(String ds_test) {
        this.ds_test = ds_test;
    }

    public String getDs_economicActivities() {
        return ds_economicActivities;
    }

    public void setDs_economicActivities(String ds_economicActivities) {
        this.ds_economicActivities = ds_economicActivities;
    }
}
