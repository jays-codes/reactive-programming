package jayslabs.reactive.sandbox.helper;

import java.time.Duration;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class JAL {
    private static final String AIRLINE = "JAL";

    /**
     * get flights from AirCanada
     * - random number of flights between 2 and 10. For each flight, 
     * random price between 100 and 1000
     * - set random delay between 200 and 1000 ms
     * @return Flux<Flight>
     */
    public static Flux<Flight> getFlights(){
        return Flux.range(1, 
            Util.faker().random().nextInt(1, 20))
        .delayElements(
            Duration.ofMillis(
                Util.faker().random().nextInt(200,800)))
        .map(i -> new Flight(AIRLINE, 
            Util.faker().random().nextInt(500, 2500)))
        .transform(Util.addFluxLogger(AIRLINE));
    }
}
