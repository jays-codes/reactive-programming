package jayslabs.reactive.sandbox.schedulers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SchedulerSubscribeOn {
    private static final Logger log = LoggerFactory.getLogger(SchedulerSubscribeOn.class);

    public static void main(String[] args) {
        var flux = Flux.create(sink -> {
            for (int i = 1; i < 3; i++) {
                log.info("emitting: {}", i);
                sink.next(i);
            }
            sink.complete();
        })
        .doOnNext(i -> log.info("onNext: {}", i))
        .doFirst(() -> log.info("doFirst1"))
        .subscribeOn(Schedulers.boundedElastic())
        .doFirst(() -> log.info("doFirst2"));

        //version 1 without Runnable
        // flux
        // .doFirst(() -> log.info("doFirst1"))
        // .subscribeOn(Schedulers.boundedElastic())
        // .doFirst(() -> log.info("doFirst2"))
        // .subscribe(Util.subscriber("anya"));

        //version 2 with Runnable
        // Runnable runnable = () -> flux
        // .doFirst(() -> log.info("doFirst1"))
        // .subscribeOn(Schedulers.boundedElastic())
        // .doFirst(() -> log.info("doFirst2"))
        // .subscribe(Util.subscriber("anya"));

        //version 3 with Runnable
        Runnable runnable1 = () -> flux.subscribe(Util.subscriber("anya"));
        Runnable runnable2 = () -> flux.subscribe(Util.subscriber("bondo"));

        Thread.ofPlatform().start(runnable1);
        Thread.ofPlatform().start(runnable2);

        Util.sleepSeconds(5);
    }

}
