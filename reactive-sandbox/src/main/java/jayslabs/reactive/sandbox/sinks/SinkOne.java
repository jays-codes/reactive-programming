package jayslabs.reactive.sandbox.sinks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Sinks;

public class SinkOne {

    private static final Logger log = LoggerFactory.getLogger(SinkOne.class);

    public static void main(String[] args) {
        //demo1();
        //demo2();    
        demo3();
    }

    private static void demo1(){
        var sink = Sinks.one();
        var mono = sink.asMono();
        mono.subscribe(Util.subscriber());

        //sink.tryEmitValue("HWORLD!!! SINK!!");
        //sink.tryEmitEmpty();
        sink.tryEmitError(new RuntimeException("error!"));
    }

    private static void demo2(){
        var sink = Sinks.one();
        var mono = sink.asMono();
        mono.subscribe(Util.subscriber("anya"));
        mono.subscribe(Util.subscriber("becky"));

        sink.tryEmitValue("HWORLD!!! SINK!!");
        
        sink.tryEmitValue("HWORLD!!! SINK2!!");

    }

    private static void demo3(){
        var sink = Sinks.one();
        var mono = sink.asMono();
        mono.subscribe(Util.subscriber("anya"));
        mono.subscribe(Util.subscriber("becky"));

        sink.emitValue("HWORLD!!! SINK3!!", (signalType, emitResult) -> {
            log.info("signalType: {}", signalType);
            log.info("emitResult: {}", emitResult);
            return false;
        });

        sink.emitValue("HWORLD!!! SINK4!!", (signalType, emitResult) -> {
            log.info("signalType: {}", signalType);
            log.info("emitResult: {}", emitResult);
            return false;
        });
    }
}
