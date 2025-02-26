package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.client.ExternalServiceClient;
import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class OperatorConcatMap {
    private static final Logger log = LoggerFactory.getLogger(OperatorConcatMap.class);

    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();
        
        Flux.range(1, 10)
        .concatMap(client::getProduct)
        .subscribe(Util.subscriber());
        
        Util.sleepSeconds(10);
    }
}
