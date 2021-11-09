package io.artur.spring.webservices.circuitbreaker.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

/**
 *
 */
@Slf4j
@Service
public class FailingService {

    public Mono <String> greetings(Optional<String> name) {

        var seconds = (long) (Math.random() * 10);

        return name.map(s -> {
            var msg = "Hello Cloud " + s + "! in " + seconds + " sec.";
            log.info(msg);
            return Mono.just(msg);
        })
            .orElse(Mono.error(new NullPointerException()))
            .delayElement(Duration.ofSeconds(seconds));
    }
}
