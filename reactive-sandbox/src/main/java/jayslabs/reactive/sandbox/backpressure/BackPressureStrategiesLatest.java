package jayslabs.reactive.sandbox.backpressure;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

public class BackPressureStrategiesLatest {

    private static final Logger log = LoggerFactory.getLogger(BackPressureStrategiesLatest.class);

    public static void main(String[] args) {


        var publisher = Flux.create(sink -> {
            for (int i = 1; i <= 1500 && !sink.isCancelled(); i++) {
                log.info("creating: {}", i);
                sink.next(i);
                Util.sleep(Duration.ofMillis(10));
            }
            sink.complete();
        }, FluxSink.OverflowStrategy.LATEST).cast(Integer.class)
        .subscribeOn(Schedulers.parallel());


        publisher
        //.onBackpressureLatest()
        .doOnDiscard(Integer.class, i -> log.warn("DROPPED: {}", i))
        //.log()
        .limitRate(1)
        .publishOn(Schedulers.boundedElastic())

        .map(BackPressureStrategiesLatest::slowTask)
        // .doOnNext(i -> {
        //     log.info("Processing item: {}", i);
        //         Util.sleepSeconds(1); // Ver
        // })
        .subscribe(
            item -> log.info("Processed: {}", item),
            error -> log.error("Error: {}", error.getMessage()),
            () -> log.info("Stream completed")

        );
        
        Util.sleepSeconds(15);
    }

    public static int slowTask(int i){
        Util.sleepSeconds(1);
        log.info("received. slowTask: {}", i);
        return i;
    }
}   
