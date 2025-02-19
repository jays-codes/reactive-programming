package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class HotPublisherAutoConnect {
    private static final Logger log = LoggerFactory.getLogger(HotPublisherAutoConnect.class);

    public static void main(String[] args) {
        var movieFlux = movieStream()
        .publish()
        .autoConnect(2);

        Util.sleepSeconds(2);
        movieFlux
        .take(4)
        .subscribe(Util.subscriber("anya"));

        Util.sleepSeconds(3);
        movieFlux
        .take(3)
        .subscribe(Util.subscriber("becky"));

        Util.sleepSeconds(15);
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
