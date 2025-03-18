package jayslabs.reactive.tests;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TimeoutTest {
    private static final Logger log = LoggerFactory.getLogger(TimeoutTest.class);

    //service class method to test
    private Flux<String> getProducts(){
        return Flux.range(1,10)
            .map(i -> "product-" + i)
            .delayElements(Duration.ofMillis(200))
            .log();
    }

    @Test
    public void testTimeout(){
        StepVerifier.create(
            getProducts())
            //.timeout(Duration.ofSeconds(3)))
        .expectNextCount(10)
        .expectComplete()
        .verify(Duration.ofMillis(2550));
    }

}