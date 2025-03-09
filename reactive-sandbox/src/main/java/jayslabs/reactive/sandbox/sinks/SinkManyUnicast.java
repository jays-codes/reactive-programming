package jayslabs.reactive.sandbox.sinks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Sinks;

public class SinkManyUnicast {

    private static final Logger log = LoggerFactory.getLogger(SinkManyUnicast.class);

    public static void main(String[] args) {
        sinkManyUnicastDemo1();
    }

    private static void sinkManyUnicastDemo1(){
        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("anya"));

        sink.emitNext("Hello Anya", (signalType, emitResult) -> {
            log.info("signalType: {}", signalType);
            log.info("emitResult: {}", emitResult);
            return false;
        });

        flux.subscribe(Util.subscriber("becky"));

        sink.emitNext("Hello Becky", (signalType, emitResult) -> {
            log.info("signalType: {}", signalType);
            log.info("emitResult: {}", emitResult);
            return false;
        });

        sink.tryEmitNext("Hello Anya2");
        sink.tryEmitNext("Hello Anya3");
        sink.tryEmitNext("Hello Anya4");

        
    }
    

}
