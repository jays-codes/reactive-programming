package jayslabs.reactive.sandbox.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class VirtualThreads {
    private static final Logger log = LoggerFactory.getLogger(VirtualThreads.class);

    public static void main(String[] args) {

        //use VT
        System
        .setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");

        var flux = Flux.create(sink -> {
            for (int i = 1; i < 3; i++) {
                log.info("emitting: {}", i);
                sink.next(i);
            }
            sink.complete();
        })
        //.subscribeOn(Schedulers.newParallel("prll-schdlr"))

        //demo immediate scheduler
        
        .doOnNext(i -> log.info("onNext: {}", i))
        .doFirst(() -> log.info("doFirst1: is VT? {}", Thread.currentThread().isVirtual()))
        .subscribeOn(Schedulers.boundedElastic())
        .doFirst(() -> log.info("doFirst2: is VT? {}", Thread.currentThread().isVirtual()));



        //version 3 with Runnable
        Runnable runnable1 = () -> flux.subscribe(Util.subscriber("anya"));
        
        Thread.ofPlatform().start(runnable1);
        
        Util.sleepSeconds(5);
    }
}
