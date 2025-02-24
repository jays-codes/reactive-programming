package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class OperatorConcatWith {
    private static final Logger log = LoggerFactory.getLogger(OperatorConcatWith.class);

    public static void main(String[] args) {
        //demo1();
        //demo2();
        demo3();
    }


    public static void demo3(){
        var flux1 = producer1();
        var flux2 = producer2();
        Flux.concat(flux2, flux1).take(2)
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    public static void demo2(){
        var flux1 = producer1();
        var flux2 = producer2();
        flux1.concatWith(flux2).take(2)
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    private static void demo1(){
        var flux1 = Flux.just("a", "b", "c");
        var flux2 = Flux.just("d", "e", "f");
        flux1.concatWith(flux2).subscribe(Util.subscriber());
    }

    private static Flux<Integer> producer1(){
        return Flux.range(1, 3)
        .doOnSubscribe(sub -> log.info("producer1 subscribed"))
        .delayElements(Duration.ofMillis(100));
    }

    private static Flux<Integer> producer2(){
        return Flux.range(51, 3)
        .doOnSubscribe(sub -> log.info("producer2 subscribed"))
        .delayElements(Duration.ofMillis(100));
    }
}
