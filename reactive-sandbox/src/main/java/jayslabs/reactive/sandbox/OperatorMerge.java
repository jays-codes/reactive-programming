package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

/**
 * modified Util.addFluxLogger to add flux logger 
 * and use it in the producers using transform
 */
public class OperatorMerge {
    private static final Logger log = LoggerFactory.getLogger(OperatorMerge.class);

    public static void main(String[] args) {
       //demo1();
        demo2();
    }

    /**
     * merge the producers and take 3 elements and rest is discarded
     */
    public static void demo1(){
        Flux.merge(
            producer1(),
            producer2(),
            producer3()
        ).take(3).subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    /**
     * mergeWith() 
     */
    public static void demo2(){
        producer1()
        .mergeWith(producer2())
          .mergeWith(producer3())
        .take(8)
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }
    private static Flux<Integer> producer1(){
        return Flux.range(1, 3)
        .transform(Util.addFluxLogger("producer1"))
        .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2(){
        return Flux.range(51, 3)
        .transform(Util.addFluxLogger("producer2"))
        .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer3(){
        return Flux.range(11, 3)
        .transform(Util.addFluxLogger("producer3"))
        .delayElements(Duration.ofMillis(10));
    }
}    
    

