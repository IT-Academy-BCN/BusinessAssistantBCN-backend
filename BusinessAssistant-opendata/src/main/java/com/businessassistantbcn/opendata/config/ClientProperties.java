package com.businessassistantbcn.opendata.config;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientProperties {

    //URL
    private Integer connection_timeout;//millis
    private String ds_test;
    private String ds_largeestablishments;
    private String ds_commercialgalleries;
    private String ds_bigmalls;
    private String ds_municipalmarkets;
    private String ds_marketfairs;
    private String ds_economicactivitiescensus;
    private String ds_economicactivitiesgroundfloor;
    private Integer maxBytesInMemory;
    private String[] districts;

    public void setMaxBytesInMemory(String maxBytesInMemory) {
        this.maxBytesInMemory = Integer.parseInt(maxBytesInMemory);
    }

    public void setConnection_timeout(String connection_timeout) {
        this.connection_timeout = Integer.parseInt(connection_timeout);
    }

    //Security
    private String secret;
    private String headerString;
    private String authoritiesClaim;
    private String err;

}
