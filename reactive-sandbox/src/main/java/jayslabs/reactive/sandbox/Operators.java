package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class Operators {
    private static final Logger log = LoggerFactory.getLogger(Operators.class);

    public static void main(String[] args) {
        Flux.range(1, 10)
        .filter(i -> i % 2 == 0)
        .map(i -> i * 10)
        
        .subscribe(Util.subscriber());
    }
}
