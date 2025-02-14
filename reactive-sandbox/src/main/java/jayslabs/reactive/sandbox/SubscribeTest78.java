package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

public class SubscribeTest78 {
    private static final Logger log = LoggerFactory.getLogger(SubscribeTest78.class);

    public static void main(String[] args) {
        Flux.range(1, 10)
        .doOnNext(i -> log.info("doOnNext(): {}", i))
        .doOnComplete(() -> log.info("doOnComplete"))
        .doOnError(e -> log.error("doOnError(): " + e))
        .subscribe();
    }
}
