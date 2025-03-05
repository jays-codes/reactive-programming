package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OperatorRepeat {

    private static final Logger log = LoggerFactory.getLogger(OperatorRepeat.class);

    
    public static void main(String[] args) {
        //repeatSolution1();
        generateMonoCountry()
        .repeat(10)
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    public static void repeatSolution1(){
        Flux.range(1, 3)
        .repeat(3)
        .subscribe(Util.subscriber());
    }

    public static Mono<String> generateMonoCountry(){
        return Mono.fromSupplier(() -> Util.faker().country().name());
    }
}
