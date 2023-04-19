package com.businessassistantbcn.gencat.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

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
    @Value("${url.ds_raisc}")
    private String dsRaisc;
    @Value("${url.error.url_malformed}")
    private String errorUrlStored;
    @Value("${url.error.empty_json}")
    private String errorEmptyJson;
    @Value("${url.error.json_updated}")
    private String errorJsonChange;
    private List<CcaeItem> ccae = new ArrayList<>();

    public int getMaxBytesInMemory() {return maxBytesInMemory;}
    public Integer getConnection_timeout() {return connection_timeout;}
    public String getDs_ccae() {return ds_ccae;}
    public String getDs_apitestcircuitbreakers() { return ds_apitestcircuitbreakers; }
    public String getDs_test() { return ds_test; }
    public String getDsRaisc() { return dsRaisc; }

    public String getDs_economicActivities() { return ds_economicActivities;}
    public String getSecret() {return secret; }
    public String getHeaderString() {return headerString; }
    public String getAuthoritiesClaim() {return authoritiesClaim;}
    public String getErr() {return err;}

    public String getErrorUrlStored() {
        return errorUrlStored;
    }

    public String getErrorEmptyJson() {
        return errorEmptyJson;
    }

    public String getErrorJsonChange() {
        return errorJsonChange;
    }

    public List<CcaeItem> getCcae() { return ccae; }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CcaeItem{
        private String type;
        private String description;
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
