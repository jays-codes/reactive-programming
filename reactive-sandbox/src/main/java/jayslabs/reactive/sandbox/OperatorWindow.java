package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OperatorWindow {
    private static final Logger log = LoggerFactory.getLogger(OperatorWindow.class);

    public static void main(String[] args) {
        //windowDemo1();
        windowDemo2();
    }
    

    private static void windowDemo1(){
        Flux.range(1, 100)
        .window(10)
        .subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }

    private static void windowDemo2(){
        streamEvents()
        //.window(5)
        .window(Duration.ofSeconds(1))
        .flatMap(flux -> processEvents(flux))
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }


    public static Mono<Void> processEvents(Flux<String> events){
        return events
        .doOnNext(e -> log.info("processing event: {}", e))
        .doOnComplete(() -> log.info("window complete"))
        .then();
    }

    private static Flux<String> streamEvents(){
        return Flux.interval(Duration.ofMillis(250))
        .take(10)
        .map(i -> "event-" + i);
    }
}
