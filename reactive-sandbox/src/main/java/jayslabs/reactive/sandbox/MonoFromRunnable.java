package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Mono;

public class MonoFromRunnable {

    private static final Logger logger = LoggerFactory.getLogger(MonoFromRunnable.class);

    private static Mono<String> getProdName(int prodId){
        if (prodId == 1){
            return Mono.fromSupplier(() -> Util.faker().commerce().productName());
        }
        //return Mono.empty();
        return Mono.fromRunnable(() -> {
            notifyBusiness(prodId);
        });
    }


    private static void notifyBusiness(int productId){
        logger.info("notifying business on unavailable product: " + productId);
    }

    public static void main(String[] args) {

        getProdName(4).subscribe(Util.subscriber());
    }


}
