package com.businessassistantbcn.opendata.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Resilience4JConfigTest {

    @Bean
    public CircuitBreakerConfigCustomizer testCustomizer() {
        return CircuitBreakerConfigCustomizer
                .of("circuitBreaker", builder -> builder.failureRateThreshold(100)
                        .minimumNumberOfCalls(100)
                        .slidingWindowSize(100));
    }
}
