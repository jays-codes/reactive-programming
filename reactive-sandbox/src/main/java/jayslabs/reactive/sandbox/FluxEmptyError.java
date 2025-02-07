package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class FluxEmptyError {
    private static final Logger log = LoggerFactory.getLogger(FluxEmptyError.class);

    public static void main(String[] args) {

        Flux.empty()
        .subscribe(Util.subscriber());

        Flux.error(new RuntimeException("Exception"))
        .subscribe(Util.subscriber());


        Flux.defer(()->{
            return Flux.just(Util.faker().name().fullName(), 
            Util.faker().name().fullName());
        }).subscribe(Util.subscriber());
    }
}
