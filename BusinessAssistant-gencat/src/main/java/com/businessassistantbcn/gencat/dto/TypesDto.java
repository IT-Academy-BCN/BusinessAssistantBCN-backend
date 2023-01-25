package com.businessassistantbcn.gencat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "ccae")
public class TypesDto {
    private List<Type> types;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Type {
        private Integer idType;
        private String type;
    }
}
