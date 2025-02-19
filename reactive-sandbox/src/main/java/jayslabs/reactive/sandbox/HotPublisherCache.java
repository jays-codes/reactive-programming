package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class HotPublisherCache {
    private static final Logger log = LoggerFactory.getLogger(HotPublisherCache.class);

    public static void main(String[] args) {
        var stockFlux = stockStream()
        .replay(1)
        .autoConnect(0);

        Util.sleepSeconds(4);
        log.info("Anya subscribing to stockFlux");
        stockFlux
        .subscribe(Util.subscriber("anya"));

        Util.sleepSeconds(3);
        log.info("Becky subscribing to stockFlux");
        stockFlux
        .subscribe(Util.subscriber("becky"));

        Util.sleepSeconds(15);
    }

    private static Flux<Integer> stockStream(){
        return Flux.generate(
            sink -> sink.next(Util.faker().random().nextInt(10,100))
        ).take(10)
        .delayElements(Duration.ofSeconds(3))
        .doOnNext(i -> log.info("emitting price: {}", i))
        .cast(Integer.class);
    }

    private static Flux<String> movieStream(){
        return Flux.generate(
            () -> {
            log.info("received request");
            return 1;
            },
            (state, sink) -> {
                var scene = "movie scene " + state;
                log.info("playing {}", scene);
                sink.next(scene);
                return ++state;
            })
            .take(10)
            .delayElements(Duration.ofSeconds(1))
            .cast(String.class);
    }
}
