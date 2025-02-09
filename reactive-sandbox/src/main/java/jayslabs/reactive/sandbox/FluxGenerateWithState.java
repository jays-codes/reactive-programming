package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class FluxGenerateWithState {

    private static final Logger log = LoggerFactory.getLogger(FluxGenerateWithState.class);

    public static void main(String[] args) {
        Flux.generate(() -> 6, (counter, sink) -> {
            var country = Util.faker().country().name();
            sink.next(country);
            if (counter == 10 || country.equalsIgnoreCase("canada")) {
                sink.complete();
            }
            return counter + 1;
        }).subscribe(Util.subscriber());

    }
}
