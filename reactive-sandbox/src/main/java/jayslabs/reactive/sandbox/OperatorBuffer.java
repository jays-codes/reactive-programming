package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class OperatorBuffer {
    private static final Logger log = LoggerFactory.getLogger(OperatorBuffer.class);

    public static void main(String[] args) {
        //bufferDemo1();
        //bufferDemo2();
        bufferDemo3();
    }
    
    private static void bufferDemo1(){
        //Flux<Integer> flux = Flux.range(1, 10);
        
        NameGenerator.generateNamesFlux(15)
        .buffer(3)
        .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    private static Flux<String> streamEvents(){
        return Flux.interval(Duration.ofMillis(300))
        .take(10)
        .map(i -> "event-" + i);
    }

    private static void bufferDemo2(){
        streamEvents()
        //.buffer(3) // buffer 3 events and emit as list
        .buffer(3) // buffer events for 2 seconds and emit as list
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    private static void bufferDemo3(){
        streamEvents()
        //.buffer(3) // buffer 3 events and emit as list
        .bufferTimeout(3, Duration.ofSeconds(1)) // buffer events for 2 seconds and emit as list
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }
}
