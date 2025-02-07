package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.helper.NameGenerator;
import reactor.core.publisher.Flux;


public class FluxCreate {
    private static final Logger log = LoggerFactory.getLogger(FluxCreate.class);

    public static void main(String[] args) {
        Flux.create(fluxSink -> {
            String country = null;
            do {
                country = Faker.instance().country().name();
                fluxSink.next(country);
            } while (!country.equals("Canada"));
            fluxSink.complete();
        }).subscribe(Util.subscriber());


        var generator = new NameGenerator();
        Flux.create(generator).subscribe(Util.subscriber());
        for (int i = 0; i < 10; i++) {
            generator.generateQuickName();
        }
    }
}
