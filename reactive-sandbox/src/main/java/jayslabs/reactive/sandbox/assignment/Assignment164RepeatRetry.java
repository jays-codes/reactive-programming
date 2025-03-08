package jayslabs.reactive.sandbox.assignment;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.client.ExternalServiceClient06;
import jayslabs.reactive.sandbox.client.ServerError;
import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

public class Assignment164RepeatRetry {

    private static final Logger log = LoggerFactory.getLogger(Assignment164RepeatRetry.class);

    public static void main(String[] args) {
        //repeatExternalClientCall();
        //retryExternalClientCall();
        test2();
    }

    public static void test2(){
        Flux.just("a")
        .repeat(1)
        .repeat(2)
        .subscribe(Util.subscriber());
    
        Util.sleepSeconds(10);
    }

    //retry when the server error occurs
    public static void retryExternalClientCall(){
        var client = new ExternalServiceClient06();
        client.getProductName(2)
        .retryWhen(
            Retry.fixedDelay(10, Duration.ofSeconds(1))
            //     .doBeforeRetry(retrySignal -> {
            //     log.info("retry attempt : {} ", retrySignal.totalRetries());
            // })
            .filter(exception -> ServerError.class.equals(exception.getClass()))
            .doBeforeRetry(retrySignal -> {
                log.info("retry attempt : {} ", retrySignal.failure().getMessage());
            })
            .onRetryExhaustedThrow((spec,signal) -> signal.failure())
        )
        .subscribe(Util.subscriber());

        Util.sleepSeconds(45);
    }

    public static void repeatExternalClientCall(){
        var client = new ExternalServiceClient06();
        
        //AtomicInteger atomicInt = new AtomicInteger(0);
        client.getCountry()
        //.repeat(() -> atomicInt.getAndIncrement() < 10)
        .repeat()
        .takeUntil(country -> country.equalsIgnoreCase("canada"))
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);

    }

}
