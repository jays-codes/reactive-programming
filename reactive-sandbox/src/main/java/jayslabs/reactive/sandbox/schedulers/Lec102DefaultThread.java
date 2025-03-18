package jayslabs.reactive.sandbox.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class Lec102DefaultThread {
    private static final Logger log = LoggerFactory.getLogger(Lec102DefaultThread.class);

    public static void main(String[] args) {
        var flux = Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                log.info("emitting: {}", i);
                sink.next(i);
            }
            sink.complete();
        })
        .doOnNext(i -> log.info("onNext: {}", i));
        
        Runnable runnable = () -> flux.subscribe(Util.subscriber("anya"));

        Thread.ofPlatform().start(runnable);

        
        
    }
}
