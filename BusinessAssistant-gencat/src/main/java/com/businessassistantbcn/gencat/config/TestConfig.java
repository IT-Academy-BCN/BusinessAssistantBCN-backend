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
}
