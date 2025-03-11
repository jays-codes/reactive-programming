package jayslabs.reactive.sandbox.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

public class ContextPropagation {

    private static final Logger log = LoggerFactory.getLogger(ContextPropagation.class);

    public static void main(String[] args) {

        getWelcomeMessage();
        Util.sleepSeconds(10);
    }

    private static void getWelcomeMessage(){
        var mono = Mono
        .deferContextual(ctx -> {
            log.info("ctx: {}", ctx);
            if (ctx.hasKey("user")){
                return Mono.just("Welcome %s ".formatted(ctx.get("user").toString()));
            }
            return Mono.error(new RuntimeException("unauthorized"));
        })
        /*
         * context propagation happens to publishers upstream the call to contextWrite
         */

        .concatWith(Flux.merge(producer1(), 
            producer2().contextWrite(ctx -> Context.empty())))
        .contextWrite(Context.of("user", "anya"));
        
        mono.subscribe(Util.subscriber());
    }

    private static Mono<String> producer1(){
        return Mono
        .<String>deferContextual(ctx -> {
            log.info("producer1 ctx: {}", ctx);
            return Mono.just("hello");
        })
        .subscribeOn(Schedulers.boundedElastic());
    }

    private static Mono<String> producer2(){
        return Mono
        .<String>deferContextual(ctx -> {
            log.info("producer2 ctx: {}", ctx);
            return Mono.just("hello");
        })
        .subscribeOn(Schedulers.parallel());
    }
}
