package jayslabs.reactive.sandbox.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.client.ExternalServiceClient;
import jayslabs.reactive.sandbox.common.Util;

public class Assignment88 {
    private static final Logger log = LoggerFactory.getLogger(Assignment88.class);
    private static final ExternalServiceClient client = new ExternalServiceClient();

    public static void main(String[] args) {
       
        for (int i = 1; i < 5; i++) {
            int productId = i;
            client.getProductNameVer3(productId)
                .subscribe(Util.subscriber());
        }
    //    getProductName(productId)
    //         .transform(handleTimeoutEmptyForGetProductName(productId))
    //         .subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }

    // public static Mono<String> getProductName(int productId){
    //     log.info("getProductName");
    //     return client.getProductNameVer2(productId);
    // }

    // public static Mono<String> getProductNameViaEmptyFallback(int productId){
    //     log.info("getProductNameViaEmptyFallback");
    //     return client.getProductNameViaEmptyFallback(productId);
    // }

    // public static Mono<String> getProductNameViaTimeoutFallback(int productId){
    //     log.info("getProductNameViaTimeoutFallback");
    //     return client.getProductNameViaTimeoutFallback(productId);
    // }

    // //handle timeout and empty mono
    // public static <T> UnaryOperator<Mono<String>> handleTimeoutEmptyForGetProductName(int productId){
        
    //     return mono -> mono
    //     .timeout(Duration.ofSeconds(20),
    //     getProductNameViaTimeoutFallback(productId))
    //     .switchIfEmpty(getProductNameViaEmptyFallback(productId));
    // }

}

