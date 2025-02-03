package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Mono;


public class PublisherCreateVsExecution {

    private static final Logger logger = LoggerFactory.getLogger(PublisherCreateVsExecution.class);

    public static void main(String[] args) {
        getName().subscribe(Util.subscriber());
    }

    private static Mono<String> getName(){
        logger.info("Generating name");
        return Mono.fromSupplier(() -> {
            logger.info("Generating name");
            Util.sleepSeconds(3);
            return Util.faker().name().fullName();
        });
    }
}

