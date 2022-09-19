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

    private String ds_ccae;

    private Integer maxBytesInMemory;

    private String ds_economicActivities;

    private int id;

    private int type;

    private int idCode;

    private int description;


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

    public String getDs_ccae() {
        return ds_ccae;
    }

    public void setDs_ccae(String ds_ccae) {
        this.ds_ccae = ds_ccae;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getIdCode() {
        return idCode;
    }

    public int getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setIdCode(int idCode) {
        this.idCode = idCode;
    }

    public void setDescription(int description) {
        this.description = description;
    }
}
