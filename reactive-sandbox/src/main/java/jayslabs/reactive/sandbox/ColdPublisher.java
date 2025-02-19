package jayslabs.reactive.sandbox;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class ColdPublisher {
    private static final Logger log = LoggerFactory.getLogger(ColdPublisher.class);

    public static void main(String[] args) {

        //multiSubscribedFlux();
        multiSubscribeNameGenerator();
    }


    private static void multiSubscribeNameGenerator(){


        var generator = new NameGenerator();
        var flux = Flux.create(generator);

        flux.subscribe(Util.subscriber("anya"));
        flux.subscribe(Util.subscriber("becky"));

        for (int i = 0; i < 3; i++) {
            generator.generateQuickName();
        }
    }

    private static void multiSubscribedFlux(){
        AtomicInteger atInt = new AtomicInteger(0);

        var flux = Flux.create(fluxsink -> {
            log.info("invoked");
            for (int i = 0; i < 5; i++) {
                fluxsink.next(atInt.getAndIncrement());
            }
            fluxsink.complete();
        });

        flux.subscribe(Util.subscriber("anya"));
        flux.subscribe(Util.subscriber("becky"));
    }
}


