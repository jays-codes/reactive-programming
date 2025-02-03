package jayslabs.reactive.sandbox;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Mono;



public class MonoDefer {

    private static final Logger log = LoggerFactory.getLogger(MonoDefer.class);
    
    public static void main(String[] args) {

        Mono.defer(MonoDefer::createPublisher);
        //.subscribe(Util.subscriber());



    }


    private static Mono<Integer> createPublisher(){
        log.info("createPublisher() method called");
        var list = List.of(1,2,3,4,5);
        Util.sleepSeconds(3);
        return Mono.fromSupplier(() -> sum(list));
    }


    private static int sum(List<Integer> list){
        log.info("sum() method called. inputs: {}", list);
        Util.sleepSeconds(3);
        return list.stream()
        .mapToInt(v -> v)
        .sum();
    }
}
