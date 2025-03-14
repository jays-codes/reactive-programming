package jayslabs.reactive.tests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {
    private static final Logger log = LoggerFactory.getLogger(MonoTest.class);

    //service class method to test
    private Mono<String> getProduct(int id){
        return Mono.fromSupplier(() -> 
            "product-" + id)
            .doFirst(() -> log.info("invoked"));
    }

    @Test
    public void testProductMono(){

        StepVerifier.create(getProduct(1))
        .expectNext("product-1")
        .expectComplete()
        .verify();
    }
}
