package jayslabs.reactive.tests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

public class ContextTest {
    private static final Logger log = LoggerFactory.getLogger(ContextTest.class);


    //service class method to test
    private Mono<String> getWelcomeMsg(){
        return Mono.deferContextual(ctx -> {
            if(ctx.hasKey("user")){
                return Mono.just("Welcome to the reactive world " + ctx.get("user"));
            }
            return Mono.error(new RuntimeException("User not found"));
        });
    }

    @Test
    public void testContext(){
        var options = StepVerifierOptions.create()
        .withInitialContext(Context.of("user", "John"));

        StepVerifier.create(getWelcomeMsg(), options)
            .expectNext("Welcome to the reactive world John")
            .verifyComplete();

    }

}
