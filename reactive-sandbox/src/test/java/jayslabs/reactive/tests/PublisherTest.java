package jayslabs.reactive.tests;

import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

public class PublisherTest {
    private static final Logger log = LoggerFactory.getLogger(PublisherTest.class);


    private UnaryOperator<Flux<String>> processor(){
        return flux -> flux
            .filter(str -> str.length() > 1)
            .map(str -> str.toUpperCase())
            .map(str -> str + ":" + str.length());
    }
    
    @Test
    public void testPublisher(){

        var publisher = TestPublisher.<String>create();
        var flux = publisher.flux();
        
        
        StepVerifier.create(flux.transform(processor()))
            .then(() -> publisher.emit("hi", "hello"))
            .expectNext("HI:2")
            .expectNext("HELLO:5")
            .verifyComplete();
    }

    @Test
    public void publisherTest2() {
        var publisher = TestPublisher.<String>create();
        var flux = publisher.flux();

        StepVerifier.create(flux.transform(processor()))
                    .then(() -> publisher.emit("a", "b", "c"))
                    .expectComplete()
                    .verify();
    }
    

}
