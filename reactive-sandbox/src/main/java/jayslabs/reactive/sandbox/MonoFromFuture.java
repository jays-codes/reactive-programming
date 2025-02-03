package jayslabs.reactive.sandbox;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Mono;


public class MonoFromFuture {

    private static final Logger logger = LoggerFactory.getLogger(MonoFromFuture.class);

    public static void main(String[] args) {
        
        Mono.fromFuture(MonoFromFuture::getName)
        .subscribe(Util.subscriber());
        Util.sleepSeconds(1);
    }


    private static CompletableFuture<String> getName(){

        return CompletableFuture.supplyAsync(() -> {
            logger.info("Generating name");
            return Util.faker().name().firstName();
        });
    }
    

}