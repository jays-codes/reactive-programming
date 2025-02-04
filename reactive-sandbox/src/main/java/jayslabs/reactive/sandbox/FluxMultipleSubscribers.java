package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class FluxMultipleSubscribers {
    private static final Logger log = LoggerFactory.getLogger(FluxMultipleSubscribers.class);


    public static void main(String[] args) {

        var flux = Flux.just(1,2,3,4,5);

        flux.subscribe(Util.subscriber("anya"));
        flux.subscribe(Util.subscriber("becky"));
        flux.filter(i -> i % 2 == 0)
            .map(i -> i + " is even")
            .subscribe(Util.subscriber("yor"));




    }

}
