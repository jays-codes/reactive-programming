package jayslabs.reactive.sandbox;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.PubUtil;
import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class OperatorZip {
    
    record Robot(String head, String body, String limbs){}

    private static final Logger log = LoggerFactory.getLogger(OperatorZip.class);
    
    public static void main(String[] args) {
        //zipDemo();
        //zipDemo2();
        zipDemo3();
        zipDemo4();
        
        
    } 


    private static void zipDemo(){
        Flux<Integer> flux1 = producer1();
        Flux<Integer> flux2 = producer2();
        Flux<Integer> flux3 = producer3();
        
        Flux.zip(flux1, flux2, flux3)
        .take(3)
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    private static void zipDemo2(){
        Flux<Integer> flux1 = producer1();
        Flux<Integer> flux2 = producer2();
        Flux<Integer> flux3 = producer3();
        
        Flux<String> fluxstr = Flux.zip(flux1, flux2, flux3)
            .map(tuple -> tuple.getT1() + "-" + tuple.getT2() + "-" + tuple.getT3());
        
        fluxstr.take(3)
            .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }


    private static void zipDemo3(){
        Flux<String> flux1 = PubUtil.createLabelledFlux(6, "head");
        Flux<String> flux2 = PubUtil.createLabelledFlux(6, "body");
        Flux<String> flux3 = PubUtil.createLabelledFlux(7, "limbs");
        
        Flux.zip(flux1, flux2, flux3)
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }
    
    /**
     * zip with record dependent on the number of 
     * elements in the fluxes
    */
    private static void zipDemo4(){
        Flux<String> flux1 = PubUtil.createLabelledFlux(6, "head");
        Flux<String> flux2 = PubUtil.createLabelledFlux(6, "body");
        Flux<String> flux3 = PubUtil.createLabelledFlux(7, "limbs");
        
        Flux.zip(flux1, flux2, flux3)
        .map(tuple -> 
        new Robot(
            tuple.getT1(),
            tuple.getT2(),
            tuple.getT3()
        ))
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }
    
    private static Flux<Integer> producer1(){
        return Flux.range(1, 5)
        .transform(Util.addFluxLogger("producer1"))
        .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2(){
        return Flux.range(51, 5)
        .transform(Util.addFluxLogger("producer2"))
        .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer3(){
        return Flux.range(11, 10)
        .transform(Util.addFluxLogger("producer3"))
        .delayElements(Duration.ofMillis(10));
    }

}
