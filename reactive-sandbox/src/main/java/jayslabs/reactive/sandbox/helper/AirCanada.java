package jayslabs.reactive.sandbox.helper;

import java.time.Duration;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class AirCanada {
    private static final String AIRLINE = "AirCanada";

    /**
     * get flights from AirCanada
     * - random number of flights between 2 and 10. For each flight, 
     * random price between 100 and 1000
     * - set random delay between 200 and 1000 ms
     * @return Flux<Flight>
     */
    public static Flux<Flight> getFlights(){
        return Flux.range(1, 
            Util.faker().random().nextInt(2, 10))
        .delayElements(
            Duration.ofMillis(
                Util.faker().random().nextInt(200,1000)))
        .map(i -> new Flight(AIRLINE, 
        Util.faker().random().nextInt(300, 1200)))
        .transform(Util.addFluxLogger(AIRLINE));
    }
}
