package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class FluxGenerate {

    private static final Logger log = LoggerFactory.getLogger(FluxGenerate.class);

    public static void main(String[] args) {
        //fluxGenerate();
        fluxCreate2Generate();
    }


    private static void fluxGenerate(){
        Flux.generate(sink -> {
            sink.next(Util.faker().country().name());
        }).log()
        .take(10)
        .log()
        .subscribe(System.out::println);

    }
    
    private static void fluxCreate2Generate(){

        //option 1
        Flux.generate(sink -> {
            sink.next(Util.faker().country().name());
        }).takeUntil(country -> country.equals("Canada"))
        .subscribe(Util.subscriber());

        //option 2
        Flux.generate(sink -> {
            var country = Util.faker().country().name();
            sink.next(country);
            if (country.equalsIgnoreCase("Canada")) {
                sink.complete();
            }
        }).subscribe(Util.subscriber());


    }


}
