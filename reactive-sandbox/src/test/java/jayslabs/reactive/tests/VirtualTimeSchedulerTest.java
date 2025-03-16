package jayslabs.reactive.tests;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class VirtualTimeSchedulerTest {
    private static final Logger log = LoggerFactory.getLogger(VirtualTimeSchedulerTest.class);

    //service class method to test
    private Flux<String> getProducts(){
        return Flux.range(1,10)
            .map(i -> "product-" + i)
            .delayElements(Duration.ofSeconds(1))
            .log();
    }

    // @Test
    // public void testProductFluxRange(){

    //     StepVerifier.create(getProducts())
    //     .expectNextCount(10)
    //     .verifyComplete();        
    // }

    @Test
    public void testProductFluxRangeWithVirtualTime(){
        StepVerifier.withVirtualTime(this::getProducts)
        .thenAwait(Duration.ofSeconds(10))
        .expectNextCount(10)
        .verifyComplete();
    }

    @Test
    public void testVirtualTimeNoevent(){
        StepVerifier.withVirtualTime(this::getProducts)
        .expectNoEvent(Duration.ofSeconds(10))
        .thenAwait(Duration.ofSeconds(10))
        .expectNextCount(10)
        .verifyComplete();
    }

}