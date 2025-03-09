package jayslabs.reactive.sandbox.sinks;

import java.time.Duration;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Sinks;

public class SinkManyMulticastDirectBestEffort {

    private static final Logger log = LoggerFactory.getLogger(SinkManyMulticastDirectBestEffort.class);

    public static void main(String[] args) {
        sinkManyMulticastDemo1();

    }

    private static void sinkManyMulticastDemo1(){

        //System.setProperty("reactor.bufferSize.small", "16");

        var sink = Sinks.many().multicast().directBestEffort();
        //.onBackpressureBuffer();
        var flux = sink.asFlux();

        //simulate disparity in processing speed between subscribers
        flux.subscribe(Util.subscriber("anya"));
        flux.onBackpressureBuffer()
        .delayElements(Duration.ofMillis(250))
        .subscribe(Util.subscriber("becky"));

        //emit 100 items from sink
        IntStream.rangeClosed(1, 100).forEach(i -> {
            log.info("emitting {} -> result {}", i, sink.tryEmitNext(i));
        });

        Util.sleepSeconds(20);
        
    }


}
