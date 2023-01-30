package com.businessassistantbcn.gencat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class PropertiesConfig {

    @Value("${url.connection_timeout}")
    private Integer connection_timeout;//millis
    @Value("${url.maxBytesInMemory}")
    private Integer maxBytesInMemory;
    @Value("${url.ds_test}")
    private String ds_test;
    @Value("${url.ds_economicActivities}")
    private String ds_economicActivities;
    @Value("${url.ds_ccae}")
    private String ds_ccae;
    @Value("${url.ds_apitestcircuitbreakers}")
    private String ds_apitestcircuitbreakers;
    @Value("${security.datasource.secret}")
    private String secret;
    @Value("${security.datasource.headerString}")
    private String headerString;
    @Value("${security.datasource.authoritiesClaim}")
    private String authoritiesClaim;
    @Value("${security.datasource.err}")
    private String err;

    public int getMaxBytesInMemory() {
        return maxBytesInMemory;
    }
    public Integer getConnection_timeout() {
        return connection_timeout;
    }
    public String getDs_ccae() {
        return ds_ccae;
    }
    public String getDs_apitestcircuitbreakers() { return ds_apitestcircuitbreakers; }
    public String getDs_test() { return ds_test; }
    public String getDs_economicActivities() { return ds_economicActivities;}
    public String getSecret() {return secret; }
    public String getHeaderString() {return headerString; }
    public String getAuthoritiesClaim() {return authoritiesClaim;}
    public String getErr() {return err;}
}
