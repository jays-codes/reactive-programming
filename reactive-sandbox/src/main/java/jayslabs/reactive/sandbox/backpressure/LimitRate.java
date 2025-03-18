package jayslabs.reactive.sandbox.backpressure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class LimitRate {

    private static final Logger log = LoggerFactory.getLogger(LimitRate.class);

    public static void main(String[] args) {

        //System.setProperty("reactor.bufferSize.small", "16");

        var publisher = Flux.generate(() -> 1,
        (state, sink) -> {
            log.info("generating: {}", state);
            sink.next(state);
            return ++state;
        }).cast(Integer.class)
        .subscribeOn(Schedulers.parallel());

        //publisher.subscribe(Util.subscriber("anya"));

        publisher
        .limitRate(3)
        .publishOn(Schedulers.boundedElastic())
        .map(LimitRate::slowTask)
        .subscribe(Util.subscriber("anya"));
        
        publisher
//        .limitRate(10)
        .take(20)
        .publishOn(Schedulers.boundedElastic())
        .subscribe(Util.subscriber("becky"));

        Util.sleepSeconds(15);
    }

    public static int slowTask(int i){
        Util.sleepSeconds(1);
        log.info("slowTask: {}", i);
        return i;
    }
}   
