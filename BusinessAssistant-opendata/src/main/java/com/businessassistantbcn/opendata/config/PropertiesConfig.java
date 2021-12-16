package com.businessassistantbcn.opendata.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("url")
public class PropertiesConfig {

    private Long connection_timeout;//millis
    private String ds_test;
    private String ds_largestablishments;
    private String ds_commercialgaleries;
    private String ds_bigmalls;
    private String ds_municipalmarkets;
    private String ds_marketsfairs;
    private String ds_economicactivitiescensus;
    private String ds_economicactivitiesgroundfloor;
    private Integer maxBytesInMemory;

    public int getMaxBytesInMemory() {
        return maxBytesInMemory;
    }

    public void setMaxBytesInMemory(String maxBytesInMemory) {
        this.maxBytesInMemory = Integer.parseInt(maxBytesInMemory);
    }

    public Long getConnection_timeout() {
        return connection_timeout;
    }

    public void setConnection_timeout(String connection_timeout) {
        this.connection_timeout = Long.parseLong(connection_timeout);
    }

    public String getDs_test() {
        return ds_test;
    }

    public void setDs_test(String ds_test) {
        this.ds_test = ds_test;
    }

    public String getDs_largestablishments() {
        return ds_largestablishments;
    }

    public void setDs_largestablishments(String ds_largestablishments) {
        this.ds_largestablishments = ds_largestablishments;
    }

    public String getDs_commercialgaleries() {
        return ds_commercialgaleries;
    }

    public void setDs_commercialgaleries(String ds_commercialgaleries) {
        this.ds_commercialgaleries = ds_commercialgaleries;
    }

    public String getDs_bigmalls() {
        return ds_bigmalls;
    }

    public void setDs_bigmalls(String ds_bigmalls) {
        this.ds_bigmalls = ds_bigmalls;
    }

    public String getDs_municipalmarkets() {
        return ds_municipalmarkets;
    }

    public void setDs_municipalmarkets(String ds_municipalmarkets) {
        this.ds_municipalmarkets = ds_municipalmarkets;
    }

    public String getDs_marketsfairs() {
        return ds_marketsfairs;
    }

    public void setDs_marketsfairs(String ds_marketsfairs) {
        this.ds_marketsfairs = ds_marketsfairs;
    }

    public String getDs_economicactivitiescensus() {
        return ds_economicactivitiescensus;
    }

    public void setDs_economicactivitiescensus(String ds_economicactivitiescensus) {
        this.ds_economicactivitiescensus = ds_economicactivitiescensus;
    }

    public String getDs_economicactivitiesgroundfloor() {
        return ds_economicactivitiesgroundfloor;
    }

    public void setDs_economicactivitiesgroundfloor(String ds_economicactivitiesgroundfloor) {
        this.ds_economicactivitiesgroundfloor = ds_economicactivitiesgroundfloor;
    }
}