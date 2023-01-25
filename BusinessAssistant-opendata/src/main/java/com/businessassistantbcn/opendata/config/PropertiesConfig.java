package com.businessassistantbcn.opendata.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("url")
public class PropertiesConfig {

    private Integer connection_timeout;//millis

    private String ds_apitestcircuitbreakers;
    private String ds_test;
    private String ds_largeestablishments;
    private String ds_commercialgalleries;
    private String ds_bigmalls;
    private String ds_municipalmarkets;
    private String ds_marketfairs;
    private String ds_economicactivitiescensus;
    private String ds_economicactivitiesgroundfloor;
    private Integer maxBytesInMemory;


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

    public String getDs_largeestablishments() {
        return ds_largeestablishments;
    }

    public void setDs_largeestablishments(String ds_largeestablishments) {
        this.ds_largeestablishments = ds_largeestablishments;
    }

    public String getDs_commercialgalleries() {
        return ds_commercialgalleries;
    }

    public void setDs_commercialgalleries(String ds_commercialgalleries) {
        this.ds_commercialgalleries = ds_commercialgalleries;
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

    public String getDs_marketfairs() {
        return ds_marketfairs;
    }

    public void setDs_marketfairs(String ds_marketfairs) {
        this.ds_marketfairs = ds_marketfairs;
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


    public String getDs_apitestcircuitbreakers() {
        return ds_apitestcircuitbreakers;
    }

    public void setDs_apitestcircuitbreakers(String ds_apitestcircuitbreakers) {
        this.ds_apitestcircuitbreakers = ds_apitestcircuitbreakers;
    }
}