package jayslabs.reactive.sandbox.helper;


import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
public class Priceline {
    private static final Logger log = LoggerFactory.getLogger(Priceline.class);

    public static Flux<Flight> getFlights() {
        return Flux.merge(
            AirCanada.getFlights(),
            Emirates.getFlights(),
            JAL.getFlights()
        )
        .take(Duration.ofSeconds(3));
    }
}
