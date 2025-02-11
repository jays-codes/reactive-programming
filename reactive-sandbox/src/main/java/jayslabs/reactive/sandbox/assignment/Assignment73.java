package jayslabs.reactive.sandbox.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class Assignment73 {
    private static final Logger log = LoggerFactory.getLogger(Assignment73.class);

    public static void main(String[] args) {
        handlerversion();
    }
    
    private static void handlerversion(){
        Flux.<String>generate(sink -> 
            sink.next(Util.faker().country().name()))
            .handle((country, sink) -> {
                sink.next(country);
                if (country.equals("Canada")) {
                    sink.complete();
                } 
            }).subscribe(Util.subscriber());

    }
}
