package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class Timeout {
    private static final Logger log = LoggerFactory.getLogger(Timeout.class);

    public static void main(String[] args) {
        timeout();
    }   

    //sample of timeout operator
    public static void timeout() {
        Flux.range(1, 10)
        .timeout(Duration.ofSeconds(3))
        .subscribe(Util.subscriber());
    }

}
