package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class OperatorStartWith {
    private static final Logger log = LoggerFactory.getLogger(OperatorStartWith.class);

    public static void main(String[] args) {
        //demo1();
        //demo2();
        //nameGeneratorDemo();
        nameGeneratorWithCacheDemo();
    }

    public static void nameGeneratorDemo(){
        var names = new NameGenerator();
        names.generateNamesFluxWithDelay().take(3).subscribe(Util.subscriber("anya"));
        names.generateNamesFluxWithDelay().take(3).subscribe(Util.subscriber("becky"));
        names.generateNamesFluxWithDelay().take(3).subscribe(Util.subscriber("bondo"));
    }

    public static void nameGeneratorWithCacheDemo(){

        var names = new NameGenerator();
        names.generateNamesFluxWithCache().take(3).subscribe(Util.subscriber("anya"));
        names.generateNamesFluxWithCache().take(3).subscribe(Util.subscriber("becky"));
        names.generateNamesFluxWithCache().take(3).subscribe(Util.subscriber("bondo"));


    }

    public static void demo1() {
        var flux = Flux.range(1, 10);
        flux.startWith(0).subscribe(Util.subscriber());
    }

    public static void demo2() {
        // var flux = producer1().startWith(producer2());
        // flux.subscribe(Util.subscriber());

        producer1().startWith(producer2()).subscribe(Util.subscriber());

    }

    private static Flux<String> producer1() {
        return Flux.range(1, 10).map(i -> "prod1-" + i);
    }

    private static Flux<String> producer2() {
        return Flux.range(1, 10).map(i -> "prod2-" + i);
    }
}
