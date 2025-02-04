package jayslabs.reactive.sandbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;


public class FluxRange {
    private static final Logger log = LoggerFactory.getLogger(FluxRange.class);

    public static void main(String[] args) {
        Flux.range(1, 10)
            .subscribe(Util.subscriber());

        Flux.range(1, 10)
            .map(i -> Faker.instance().name().firstName())
            .subscribe(Util.subscriber());

    }
}
