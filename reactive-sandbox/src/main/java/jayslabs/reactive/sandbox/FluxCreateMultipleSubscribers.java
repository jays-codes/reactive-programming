package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.helper.NameGenerator;
import reactor.core.publisher.Flux;


public class FluxCreateMultipleSubscribers {
    private static final Logger log = LoggerFactory.getLogger(FluxCreateMultipleSubscribers.class);

    public static void main(String[] args) {
        var generator = new NameGenerator();
        var flux = Flux.create(generator).share();

        flux
        .subscribe(Util.subscriber("anya"));
        flux
        .subscribe(Util.subscriber("becky"));
        
        for (int i = 0; i < 10; i++) {
            generator.generateQuickName();
        }
    }
}
