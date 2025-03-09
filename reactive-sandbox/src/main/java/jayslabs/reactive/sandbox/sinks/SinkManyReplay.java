package jayslabs.reactive.sandbox.sinks;

import java.time.Duration;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Sinks;

public class SinkManyReplay {

    private static final Logger log = LoggerFactory.getLogger(SinkManyReplay.class);

    public static void main(String[] args) {
        demoSinkManyReplay();

    }

    private static void demoSinkManyReplay(){

        //System.setProperty("reactor.bufferSize.small", "16");

        var sink = Sinks.many().replay().all();
        //.onBackpressureBuffer();
        var flux = sink.asFlux();

        //simulate disparity in processing speed between subscribers
        flux.subscribe(Util.subscriber("anya"));
        flux
        //.onBackpressureBuffer()
        .delayElements(Duration.ofMillis(250))
        .subscribe(Util.subscriber("becky"));

        //emit 100 items from sink
        IntStream.rangeClosed(1, 100).forEach(i -> {
            log.info("emitting {} -> result {}", i, sink.tryEmitNext(i));
        });

        flux.subscribe(Util.subscriber("bondo"));


        Util.sleepSeconds(60);
        

    }

}
