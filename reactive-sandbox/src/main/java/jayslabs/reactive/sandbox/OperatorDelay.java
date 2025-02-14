package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class OperatorDelay {
    private static final Logger log = LoggerFactory.getLogger(OperatorDelay.class);

    public static void main(String[] args) {
        Flux.range(1, 10)
        .log()
        .delayElements(Duration.ofSeconds(1))
        .subscribe(Util.subscriber());

        Util.sleepSeconds(12);
    }
}
