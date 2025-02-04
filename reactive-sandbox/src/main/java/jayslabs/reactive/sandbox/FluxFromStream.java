package jayslabs.reactive.sandbox;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class FluxFromStream {
    private static final Logger log = LoggerFactory.getLogger(FluxFromStream.class);

    public static void main(String[] args) {

        var list = List.of("A", "B", "C", "D", "E", "F");
        //var stream = list.stream();

        var flux = Flux.fromStream(list::stream);
        flux.subscribe(Util.subscriber("anya"));
        flux.subscribe(Util.subscriber("becky"));
        flux.subscribe(Util.subscriber("yor"));


    }

}
