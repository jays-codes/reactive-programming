package jayslabs.reactive.sandbox.sinks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Sinks;

public class SinkManyMulticast {

    private static final Logger log = LoggerFactory.getLogger(SinkManyMulticast.class);

    public static void main(String[] args) {
        //sinkManyMulticastDemo1();
        sinkManyMulticastDemo2();
    }

    private static void sinkManyMulticastDemo1(){
        var sink = Sinks.many().multicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("anya"));
        flux.subscribe(Util.subscriber("becky"));

        sink.tryEmitNext("Hello Anya");
        sink.tryEmitNext("Hello Becky");
        
        flux.subscribe(Util.subscriber("bondo"));

        sink.tryEmitNext("Hello Bondo");

        Util.sleepSeconds(2);
        
    }

    //warmup behavior
    private static void sinkManyMulticastDemo2(){
        var sink = Sinks.many().multicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        sink.tryEmitNext("Hello Anya");
        sink.tryEmitNext("Hello Becky");        
        sink.tryEmitNext("Hello Bondo");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("anya"));
        flux.subscribe(Util.subscriber("becky"));

    }

}
