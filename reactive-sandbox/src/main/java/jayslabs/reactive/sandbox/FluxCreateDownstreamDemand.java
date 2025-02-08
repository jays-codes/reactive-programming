package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.subscriber.SubscriberImpl;
import reactor.core.publisher.Flux;


public class FluxCreateDownstreamDemand {

    private static final Logger log = LoggerFactory.getLogger(FluxCreateDownstreamDemand.class);

    public static void main(String[] args) {

        var subscriber = new SubscriberImpl();

        Flux.<String>create(fluxsink -> {
            for (int i = 0; i < 10; i++) {
                var name = Util.faker().name().firstName();
                log.info("generated: {}", name);
                fluxsink.next(name);
            }
            fluxsink.complete();
        }).subscribe(subscriber);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);

    }
}
