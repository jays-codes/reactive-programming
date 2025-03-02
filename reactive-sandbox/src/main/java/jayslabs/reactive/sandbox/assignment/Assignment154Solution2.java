package jayslabs.reactive.sandbox.assignment;

import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
public class Assignment154Solution2 {
    private static final Logger log = LoggerFactory.getLogger(Assignment154Solution2.class);

    public static void main(String[] args) {

        var counter = new AtomicInteger(0);
        var filePath = System.getProperty("user.dir") 
        + "/reactive-sandbox/src/main/resources/files/assignment154/" 
        + "events-%d.txt";

        streamEvents()
        .window(Duration.ofMillis(2000))
        .flatMap(flux -> EventLoggerUtil.processEvents(
            flux, 
            Path.of(filePath.formatted(counter.getAndIncrement()))))
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    private static Flux<String> streamEvents(){
        return Flux.interval(Duration.ofMillis(100))
        .map(i -> "event-" + i);
    }


}   
