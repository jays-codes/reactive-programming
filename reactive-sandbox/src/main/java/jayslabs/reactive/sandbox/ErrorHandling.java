package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ErrorHandling {
    private static final Logger log = LoggerFactory.getLogger(ErrorHandling.class);

    public static void main(String[] args) {
        callOnErrorReturn();
        callFallback();
    }

    public static void callOnErrorReturn() {
        Flux.range(1, 10)
        .map(i -> i==5 ? 5/0 : i)
        .onErrorReturn(-1)
        .subscribe(Util.subscriber());
    }

    //call fallback method
    public static void callFallback() {
        Flux.range(1, 10)
        .map(i -> i==5 ? 5/0 : i)
        .onErrorResume(ArithmeticException.class, e -> fallback())
        .subscribe(Util.subscriber());
    }

    public static Mono<Integer> fallback() {
        return Mono.just(Util.faker().random().nextInt(100, 200));
    }
    
}
