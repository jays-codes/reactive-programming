package jayslabs.reactive.sandbox;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.helper.NameGenerator;
import reactor.core.publisher.Flux;




public class FluxSinkThreadSafety {

    private static final Logger log = LoggerFactory.getLogger(FluxSinkThreadSafety.class);

    public static void main(String[] args) {
        demo2();
    }


    // demo1: thread unsafe Collection
    // A runnable trying to add 1000 items to a list
    // 10 threads are running this runnable with expectation that 
    // the list size will be 10000
    private static void demo1(){
        var list = new ArrayList<Integer>();
        Runnable runnable = () -> {

            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }
        Util.sleepSeconds(3);
        
        log.info("list size: {}", list.size());
    }

    // demo2: thread safe using FluxSink
    private static void demo2(){
        var list = new ArrayList<String>();
        var generator = new NameGenerator();
        //creates a Flux using the NameGenerator as the source
        Flux.create(generator)
        //adds each emitted item to the list. No items are emitted yet 
        .subscribe(list::add);
     

        Runnable runnable = () -> {

            for (int i = 0; i < 1000; i++) {
                //this will emit items to the Flux
                generator.generateQuickName();
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }
        Util.sleepSeconds(3);
        
        log.info("list size: {}", list.size());
    }
}

