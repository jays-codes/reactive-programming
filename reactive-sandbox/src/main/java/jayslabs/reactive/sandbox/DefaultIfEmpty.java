package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class DefaultIfEmpty {

    private static final Logger log = LoggerFactory.getLogger(DefaultIfEmpty.class);

    public static void main(String[] args) {
        Flux.range(1, 10)
        .filter(i -> i>9)
        .doOnDiscard(Integer.class, i -> log.info("Not emitted: {}", i))
        .defaultIfEmpty(-100)
        .subscribe(Util.subscriber());

    }
}
