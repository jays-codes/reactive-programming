package jayslabs.reactive.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoEmptyErrorTest {
    private static final Logger log = LoggerFactory.getLogger(MonoEmptyErrorTest.class);

    //service class method to test
    private Mono<String> getProduct(int id){
        return Mono.fromSupplier(() -> 
            "product-" + id)
            .doFirst(() -> log.info("invoked"));
    }

    Mono<String> getUserName(int userId){
        return switch (userId){
            case 1 -> Mono.just("anya");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("Invalid user id"));
        };
    }

    @Test
    public void testGetUserName(){

        StepVerifier.create(getUserName(1))
        .expectNext("anya")
        .verifyComplete();
     }

    @Test
    public void testGetUserNameEmpty(){
        StepVerifier.create(getUserName(2))
        .expectComplete()
        .verify();
    }

    @Test
    public void testGetUserNameError(){
        StepVerifier.create(getUserName(4))
        .expectError(RuntimeException.class)
        .verify();
    }

    @Test
    public void testGetUserNameErrorMessage(){
        StepVerifier.create(getUserName(4))
        .expectErrorMessage("Invalid user id")
        .verify();
    }

    @Test
    public void testGetUserNameError2(){
        StepVerifier.create(getUserName(4))
        .consumeErrorWith(ex -> {
            Assertions.assertEquals(ex.getClass(), RuntimeException.class);
        })
        .verify();
    }
}
