package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Mono;
public class Timeout {
    private static final Logger log = LoggerFactory.getLogger(Timeout.class);

    public static void main(String[] args) {
        //timeout();

        getProductName()
        .timeout(Duration.ofSeconds(2), 
        getProductNameFallback().timeout(Duration.ofMillis(300)))
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }   

    //sample of timeout operator
    // public static void timeout() {
    //     Flux.range(1, 5)
    //     .delayElements(Duration.ofSeconds(2))
    //     .timeout(Duration.ofSeconds(1))
    //     .onErrorReturn(999)
    //     .subscribe(Util.subscriber());

    //     Util.sleepSeconds(10);
    // }

    public static Mono<String> getProductNameFallback() {
        return Mono.fromSupplier(() -> "fallback-" + Util.faker().commerce().productName())
        .delayElement(Duration.ofMillis(300))
        .doFirst(()->log.info("getProductNameFallback doFirst()"));
    }

    

    //sample of timeout operator
    public static Mono<String> getProductName() {
        return Mono.fromSupplier(() -> Util.faker().commerce().productName())
        .delayElement(Duration.ofSeconds(3));
    }
}
