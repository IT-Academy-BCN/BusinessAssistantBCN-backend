
package com.businessassistantbcn.opendata.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/*@Configuration
@Profile("test")*/
public class Resilience4JConfig {

    @Autowired
    private static PropertiesConfig config;

    @Bean
    public CircuitBreakerConfigCustomizer testCustomizer() {
        return CircuitBreakerConfigCustomizer
                .of("circuitBreaker", builder -> builder.failureRateThreshold(100)
                                                                    .minimumNumberOfCalls(100)
                                                                    .slidingWindowSize(100));
    }


}



