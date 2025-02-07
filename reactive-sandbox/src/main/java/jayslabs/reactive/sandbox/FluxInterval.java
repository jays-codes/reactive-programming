package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class FluxInterval {
    private static final Logger log = LoggerFactory.getLogger(FluxInterval.class);

    public static void main(String[] args) {
        Flux.interval(Duration.ofMillis(500))
            .map(i -> Util.faker().name().firstName())
            .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }
}
