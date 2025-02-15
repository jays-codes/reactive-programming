package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class SwitchIfEmpty {
    private static final Logger log = LoggerFactory.getLogger(SwitchIfEmpty.class);

    public static void main(String[] args) {
        Flux.just(1, 2, 3, 4, 5)
        .filter(i -> i>9)
        .switchIfEmpty(getFallback())
        .defaultIfEmpty(-100)
        .subscribe(Util.subscriber());
    }

    public static Flux<Integer> getFallback() {
        log.info("getFallback");
        return Flux.range(100, 3).filter(p -> p>105);
    }
    
    
}
