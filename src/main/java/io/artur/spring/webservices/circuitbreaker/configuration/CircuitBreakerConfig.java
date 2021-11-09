package io.artur.spring.webservices.circuitbreaker.configuration;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Function;

/**
 *
 */
@Configuration
public class CircuitBreakerConfig {

    @Bean
    public ReactiveCircuitBreaker createCB(ReactiveCircuitBreakerFactory factory) {
        return factory.create("greet");
    }

    @Bean
    public ReactiveCircuitBreakerFactory createFactory() {
        var factory = new ReactiveResilience4JCircuitBreakerFactory();

        factory.configureDefault(s -> new Resilience4JConfigBuilder(s)
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
                .circuitBreakerConfig(io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.ofDefaults())
                .build());

        return factory;
    }
}
