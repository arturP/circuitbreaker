package io.artur.spring.webservices.circuitbreaker.controllers;

import io.artur.spring.webservices.circuitbreaker.services.FailingService;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

/**
 *
 */
@RequiredArgsConstructor
@RestController
public class FailingController {

    private final FailingService service;
    private final ReactiveCircuitBreaker circuitBreaker;

    @GetMapping("/greet")
    Publisher<String> greet(@RequestParam Optional<String>name) {
        var result = service.greetings(name);

        return circuitBreaker.run(result, throwable -> Mono.just("Hi there!!!"));
    }
}
