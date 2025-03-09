package jayslabs.reactive.sandbox.sinks;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Sinks;

public class SinkThreads {

    private static final Logger log = LoggerFactory.getLogger(SinkThreads.class);

    public static void main(String[] args) {
        sinkThreadsDemo1();
    }

    private static void sinkThreadsDemo1(){
        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();
        
        //flux.subscribe(Util.subscriber("anya"));
        //sink.tryEmitNext("Hello Anya");

        //try multi thread on arraylist as base via CompletableFuture
        var list = new ArrayList<>();
        flux.subscribe(list::add);
        
        for (int i = 0; i < 1000; i++) {
            var j=i;
           CompletableFuture.runAsync(() -> {
            sink.emitNext(j, (signalType, emitResult) -> {
                log.info("signalType: {}", signalType);
                log.info("emitResult: {}", emitResult);
                return Sinks.EmitResult.FAIL_NON_SERIALIZED.equals(emitResult);
            });
           });
        }

        Util.sleepSeconds(2);
        log.info("list size: {}", list.size());
    }
}
