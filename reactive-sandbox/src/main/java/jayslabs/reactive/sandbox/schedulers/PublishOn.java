package jayslabs.reactive.sandbox.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class PublishOn {

    private static final Logger log = LoggerFactory.getLogger(PublishOn.class);

    public static void main(String[] args) {


        var flux = Flux.create(sink -> {
            for (int i = 1; i < 3; i++) {
                log.info("emitting: {}", i);
                sink.next(i);
            }
            sink.complete();
        })
        .publishOn(Schedulers.newParallel("prll-schdlr"))      
        .doOnNext(i -> log.info("onNext: {}", i))
        .doFirst(() -> log.info("doFirst1"))
        .publishOn(Schedulers.boundedElastic())
        .doFirst(() -> log.info("doFirst2"));



        //version 3 with Runnable
        Runnable runnable1 = () -> flux.subscribe(Util.subscriber("anya"));
        
        Thread.ofPlatform().start(runnable1);
        
        Util.sleepSeconds(5);
    }
}
