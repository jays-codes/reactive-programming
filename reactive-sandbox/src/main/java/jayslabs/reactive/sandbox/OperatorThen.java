package jayslabs.reactive.sandbox;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OperatorThen {
    private static final Logger log = LoggerFactory.getLogger(OperatorThen.class);

    public static void main(String[] args) {
        // Flux.range(1, 10)
        //     .then()
        //     .subscribe(Util.subscriber());

        var records = List.of("a", "b", "c");
        saveRecords(records)
            .then(sendNotification(records))
            .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    private static Flux<String> saveRecords(List<String> records) {
        return Flux.fromIterable(records)
                   .map(r -> "saved " + r)
                   .delayElements(Duration.ofMillis(500));
    }

    private static Mono<Void> sendNotification(List<String> records) {
        return Mono.fromRunnable(() -> log.info("all these {} records saved successfully", records));
    }
}
