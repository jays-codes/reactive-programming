package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;
public class MonoSubscribe {

    private static final Logger logger = LoggerFactory.getLogger(MonoSubscribe.class);

    public static void main(String[] args) {

        var mono = Mono.just("MONO HWorld!!!")
        .map(str -> str + ":" + str.length()/0);

        //var subscriber = new SubscriberImpl();

        mono.subscribe(
            System.out::println,
            err -> logger.error("Error on MONO: {}", err),
            () -> logger.info("Completed"),
            //subscription -> subscription.cancel()
            subscription -> subscription.request(10)
            );
    }
}
