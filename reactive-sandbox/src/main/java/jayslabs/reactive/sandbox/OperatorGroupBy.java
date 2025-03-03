package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

public class OperatorGroupBy {
    private static final Logger log = LoggerFactory.getLogger(OperatorGroupBy.class);


    public static void main(String[] args) {

        //groupByDemo1();
        groupByDemo2();

    }

    private static void groupByDemo2(){
        var flux = Flux.range(1,30);
        flux.groupBy(i -> i % 2 == 0 ? "even" : "odd")
        .flatMap(gf -> processDemo2(gf))
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    private static Mono<Void> processDemo2(GroupedFlux<String, Integer> gf){
        log.info("processing group: {}", gf.key());
        
        return gf
        .doOnNext(e -> log.info("processing element: key: {}, element: {}", gf.key(), e))
        .doOnComplete(() -> log.info("completed processing group: {}", gf.key()))
        .then();
    }

    private static void groupByDemo1(){
        var flux = Flux.range(1,30);
        flux.groupBy(i -> i % 2 == 0 ? "even" : "odd")
        .flatMap(gf -> processDemo1(gf, gf.key()))
        .subscribe(Util.subscriber());
    }

    private static Flux<String> processDemo1(Flux<Integer> flux, String key){
        return flux
        .map(i -> key + "-" + i)
        .doOnNext(e -> log.info("processing event: {}", e));
    }

    private static Flux<String> streamEvents(){
        return Flux.interval(Duration.ofMillis(100))
        .map(i -> "event-" + i);
    }
}
