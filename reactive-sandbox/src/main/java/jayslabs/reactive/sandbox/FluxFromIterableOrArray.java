package jayslabs.reactive.sandbox;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class FluxFromIterableOrArray {
    private static final Logger log = LoggerFactory.getLogger(FluxFromIterableOrArray.class);

    public static void main(String[] args) {

        var list = List.of("A", "B", "C");
        var intarr = new Integer[] {1,2,3};


        var flux = Flux.fromIterable(list);
        flux.subscribe(Util.subscriber());

        var flux2 = Flux.fromArray(intarr);
        flux2.subscribe(Util.subscriber());


    }

}
