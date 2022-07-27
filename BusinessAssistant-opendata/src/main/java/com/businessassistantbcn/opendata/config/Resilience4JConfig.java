
package com.businessassistantbcn.opendata.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Configuration
public class Resilience4JConfig {

    @Autowired
    private static PropertiesConfig config;

    @Profile("dev")
    public static class Resilience4JConfigDev {

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .minimumNumberOfCalls(4)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .build();


        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4))
                .build();

        @Bean
        public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizerDev() {
            return factory -> factory.configure(builder -> builder.circuitBreakerConfig(circuitBreakerConfig)
                    .timeLimiterConfig(timeLimiterConfig).build(), "circuitBreaker");
        }

        /*@Bean
        public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizerDev() {
            return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(CircuitBreakerConfig.custom()
                            .minimumNumberOfCalls(4)
                            .waitDurationInOpenState(Duration.ofSeconds(5))
                            .build())
                    .build());
        }*/
    }

    @Profile("test")
    public static class Resilience4JConfigTest {

        @Bean
        public CircuitBreakerConfigCustomizer testCustomizer() {
            return CircuitBreakerConfigCustomizer
                    .of("circuitBreaker", builder -> builder.failureRateThreshold(100)
                                                                        .minimumNumberOfCalls(100)
                                                                        .slidingWindowSize(100)
                                                                        .waitDurationInOpenState(Duration.ofMillis(1)));
        }

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(100)
                .minimumNumberOfCalls(100)
                .slidingWindowSize(100)
                .waitDurationInOpenState(Duration.ofMillis(1))
                .build();


        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4))
                .build();

        /*@Bean
        public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizerTest() {
            return factory -> factory.configure(builder -> builder.circuitBreakerConfig(circuitBreakerConfig)
                    .timeLimiterConfig(timeLimiterConfig).build(), "circuitBreaker");
        }*/

        @Bean
        public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizerTest() {
            return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(CircuitBreakerConfig.custom()
                                                                .failureRateThreshold(100)
                                                                .minimumNumberOfCalls(100)
                                                                .slidingWindowSize(100)
                                                                .waitDurationInOpenState(Duration.ofMillis(1)).build())
                    .build());
        }

    }

}



