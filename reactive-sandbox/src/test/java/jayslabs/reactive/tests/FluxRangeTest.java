package jayslabs.reactive.tests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxRangeTest {
    private static final Logger log = LoggerFactory.getLogger(FluxRangeTest.class);

    //service class method to test
    private Flux<String> getProducts(){
        return Flux.range(1,30)
            .map(i -> "product-" + i)
            .log();
    }

    @Test
    public void testProductFluxRange(){

        StepVerifier.create(getProducts())
        .expectNextCount(30)
        .verifyComplete();        
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