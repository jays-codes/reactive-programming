package jayslabs.reactive.sandbox.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Mono;

public class Context {

    private static final Logger log = LoggerFactory.getLogger(Context.class);

    public static void main(String[] args) {

        //demoContext();
        getWelcomeMessage();


        Util.sleepSeconds(10);
    }

    private static void getWelcomeMessage(){
        Mono<String> mono = Mono.deferContextual(ctx -> {
            log.info("ctx: {}", ctx);
            if (ctx.hasKey("user")){
                return Mono.just("Welcome %s ".formatted(ctx.get("user").toString()));
            }
            return Mono.error(new RuntimeException("unauthorized"));
        })

        .contextWrite(ctx -> ctx.put("userkey", "bondo"));
        mono.subscribe(Util.subscriber());
    }

}
