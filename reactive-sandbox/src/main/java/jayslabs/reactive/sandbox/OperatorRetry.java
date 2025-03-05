package jayslabs.reactive.sandbox;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class OperatorRetry {

    private static final Logger log = LoggerFactory.getLogger(OperatorRetry.class);

    public static void main(String[] args) {
        //retry3times();
        retryWhenTest();
    }

    public static void retryWhenTest(){
        generateMonoCountry()
        //.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
        //.retryWhen(Retry.indefinitely())
        //.retryWhen(Retry.max(3))
        .retryWhen(
            Retry.fixedDelay(2, Duration.ofSeconds(2))
            //     .doBeforeRetry(retrySignal -> {
            //     log.info("retry attempt : {} ", retrySignal.totalRetries());
            // })
            .filter(exception -> exception instanceof RuntimeException)
            .onRetryExhaustedThrow((spec,signal) -> signal.failure())
        )
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    public static void retry3times(){
        generateMonoCountry()
        .retry(3)
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    public static Mono<String> generateMonoCountry(){
        var atomicInt = new AtomicInteger(0);
        return Mono.fromSupplier(() -> {
            if (atomicInt.getAndIncrement() < 3) {
                throw new RuntimeException("test exception");
            }
            return Util.faker().country().name();
        })
        .doOnError(err -> log.error("error : {} ", err.getMessage()))
        .doOnSubscribe(sub -> log.info("subscribed"));
    }
    
}   
