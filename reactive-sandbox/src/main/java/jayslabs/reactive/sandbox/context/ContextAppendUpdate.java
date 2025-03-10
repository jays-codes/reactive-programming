package jayslabs.reactive.sandbox.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class ContextAppendUpdate {

    private static final Logger log = LoggerFactory.getLogger(ContextAppendUpdate.class);

    public static void main(String[] args) {

        getWelcomeMessage();
        Util.sleepSeconds(10);
    }

    private static void getWelcomeMessage(){
        Mono<String> mono = Mono
        .deferContextual(ctx -> {
            log.info("ctx: {}", ctx);
            if (ctx.hasKey("user")){
                return Mono.just("Welcome %s ".formatted(ctx.get("user").toString()));
            }
            return Mono.error(new RuntimeException("unauthorized"));
        })

        //.contextWrite(ctx -> ctx.put("userkey", "bondo"));

        //append to existing context
        // .contextWrite(
        //     Context.of("user", "bondo").put("userkey2", "bondo2"));
        // mono.subscribe(Util.subscriber());

        //update existing context
        // .contextWrite(
        //     Context.of("user", "bondo").put("user", "anya-modified"));
        
        //empty context
        .contextWrite(Context.empty());
        
        mono.subscribe(Util.subscriber());
    }

}
