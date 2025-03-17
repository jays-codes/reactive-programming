package jayslabs.reactive.tests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

public class ScenarioNameTest {
    private static final Logger log = LoggerFactory.getLogger(ScenarioNameTest.class);


    //service class method to test
    private Flux<String> getProducts(){
        return Flux.range(1,5)
            .map(i -> "product-" + i)
            .log();
    }

    @Test
    public void testScenarioName(){

        var options = StepVerifierOptions.create()
        .scenarioName("Products test");

        StepVerifier.create(getProducts(), options)
            .expectNext("product-1")
            .as("first product")
            .expectNext("product-2")
            .as("second product")
            .expectNext("product-3")
            .as("third product")
            .expectNext("product-4")
            .as("fourth product")
            .expectNext("product-5")
            .as("fifth product")
            .verifyComplete();
    }

}
