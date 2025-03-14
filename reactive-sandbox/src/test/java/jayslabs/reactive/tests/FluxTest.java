package jayslabs.reactive.tests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {
    private static final Logger log = LoggerFactory.getLogger(FluxTest.class);

    //service class method to test
    private Flux<String> getProducts(){
        return Flux.just(1,2,3)
            .map(i -> "product-" + i)
            .log();
    }

    @Test
    public void testProductFlux(){

        StepVerifier.create(getProducts(),2)
        .expectNext("product-1")
        .expectNext("product-2")
        .thenCancel()
        
//        .expectNext("product-3")
        .verify();
    }

    @Test
    public void testProductFluxMap(){
        StepVerifier.create(getProducts())

        //expect for String value
        .expectNextMatches(prdct -> prdct.startsWith("product-"))
        .thenCancel()
        .verify();
        //.verifyComplete();


    }
}