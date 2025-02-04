package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

public class FluxJust {
    private static final Logger log = LoggerFactory.getLogger(FluxJust.class);


    public static void main(String[] args) {
        Flux.just("A", "B", "C");
            // .subscribe(Util.subscriber());
    }

}
