package jayslabs.reactive.sandbox;

import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;


public class TakeOperator {


    private static final Logger log = LoggerFactory.getLogger(TakeOperator.class);
    public static void main(String[] args) {
        //streamLimit();
        //fluxTake();
        //fluxTakeWhile();
        fluxTakeUntil();
    }



    private static void streamLimit(){
        IntStream.rangeClosed(1, 10)
        .limit(3)
        .forEach(System.out::println);
    }

    private static void fluxTake(){
        Flux.range(1, 10)
            .log("take")
        .take(3)
            .log("subscriber")
        .subscribe(Util.subscriber());
    }

    private static void fluxTakeWhile(){
        Flux.range(1, 10)
        .log("takeWhile")
        .takeWhile(i -> i < 5)
        .log("subscriber")
        .subscribe(Util.subscriber());
    }

    private static void fluxTakeUntil(){
        Flux.range(1, 10)
        //.log("takeUntil")
        .takeUntil(i -> i == 5)
        //.log("subscriber")
        .subscribe(System.out::println);

    }



}
