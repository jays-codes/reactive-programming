package jayslabs.reactive.sandbox;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OperatorRepeat {

    private static final Logger log = LoggerFactory.getLogger(OperatorRepeat.class);

    
    public static void main(String[] args) {
        //repeatSolution1();
        //repeatTakeUntil();
        //repeatPredicate();
        repeatWhenDelay();

        Util.sleepSeconds(10);
    }

    public static void repeatWhenDelay(){
        generateMonoCountry()
        .repeatWhen(rSignal -> 
            rSignal.delayElements(Duration.ofSeconds(2))
            
            ).take(2)
        .subscribe(Util.subscriber());
    }

    public static void repeatPredicate(){
        AtomicInteger atomicInt = new AtomicInteger(0);
        generateMonoCountry()
        .repeat(() -> atomicInt.getAndIncrement() < 10)
        .subscribe(Util.subscriber());
    }

    public static void repeatTakeUntil(){
        generateMonoCountry()
        .repeat()
        .takeUntil(country -> country.equals("Canada"))
        .subscribe(Util.subscriber());

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
