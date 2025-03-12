package jayslabs.reactive.sandbox.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.client.ExternalServiceClient07;
import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ContextRateLimiter {
    private static final Logger log = LoggerFactory.getLogger(ContextRateLimiter.class);

    private static final Map<String, Integer> categoryAttempts = 
        Collections.synchronizedMap(new HashMap<>());

    static {
        categoryAttempts.put("standard", 2);
        categoryAttempts.put("premium", 3);
    }

    static <T> Mono<T> limitCalls(){
        return Mono.deferContextual(ctx -> {

            var allowCall = ctx.<String>getOrEmpty("category")
            .map(ContextRateLimiter::allows)
            .orElse(false);

            return allowCall ? 
                Mono.empty()
                : Mono.error(new RuntimeException("rate limit exceeded"));
        });
    }

    private static boolean allows(String category){
        var attempts = categoryAttempts.getOrDefault(category, 0);
        if (attempts > 0){  
            categoryAttempts.put(category, attempts - 1);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

        var client = new ExternalServiceClient07();

        //rate limiter limit to 2 calls per 5 seconds for regular user, 
        //3 calls per 5 seconds for premium user. Use a context

        var flux = Flux.range(1, 10)
        .doOnNext(i -> log.info("processing item: {}", i))
        .flatMap(i -> client.getBook());


        flux.contextWrite(ctx -> {
            if (ctx.get("user").equals("premium")){
                return ctx.put("calls", 3);
            }
            return ctx.put("calls", 2);
        })
        .subscribe(Util.subscriber());


        Util.sleepSeconds(5);
    }


}
