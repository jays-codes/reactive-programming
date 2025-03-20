package jayslabs.reactive.sandbox.backpressure;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
public class BackPressureStrategies {

    private static final Logger log = LoggerFactory.getLogger(BackPressureStrategies.class);

    public static void main(String[] args) {


        var publisher = Flux.create(sink -> {
            for (int i = 1; i <= 1500 && !sink.isCancelled(); i++) {
                log.info("creating: {}", i);
                sink.next(i);
                Util.sleep(Duration.ofMillis(50));
            }
            sink.complete();
        }).cast(Integer.class)
        .subscribeOn(Schedulers.parallel());


        publisher

        //buffer strategy
        //.onBackpressureBuffer()

        //error strategy
        //.onBackpressureError()
        
        //fixed size buffer
        .onBackpressureBuffer(100)
        .publishOn(Schedulers.boundedElastic())
        .map(BackPressureStrategies::slowTask)
        .subscribe();
        

        Util.sleepSeconds(15);
    }

    public static int slowTask(int i){
        Util.sleepSeconds(1);
        log.info("received. slowTask: {}", i);
        return i;
    }
}   
