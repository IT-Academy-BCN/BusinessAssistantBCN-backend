package com.businessassistantbcn.gencat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class TestConfig {

    private List<CcaeItem> ccae = new ArrayList<>();
    private Datatest datatest = new Datatest();

        public static class Datatest {
            private String secret;
            private String headerString;
            private String authoritiesClaim;
            private String err;

            public String getSecret() {return secret; }
            public void setSecret(String secret) { this.secret = secret;}
            public String getHeaderString() { return headerString; }
            public void setHeaderString(String headerString) { this.headerString = headerString; }
            public String getAuthoritiesClaim() { return authoritiesClaim; }
            public void setAuthoritiesClaim(String authoritiesClaim) { this.authoritiesClaim = authoritiesClaim; }
            public String getErr() {return err;}
            public void setErr(String err) { this.err = err; }
        }

    public static class CcaeItem{
        private String type;
        private String description;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public List<CcaeItem> getCcae() {
        return ccae;
    }
    public Datatest getDatatest() {
        return datatest;
    }
}
