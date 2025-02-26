package jayslabs.reactive.sandbox.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.client.ExternalServiceClient;
import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

/*
 * modified Assignment136 to use Flux<Integer> 
 * instead of a for-loop, and use flatMap()
 */
public class Assignment142 {
    private static final Logger log = LoggerFactory.getLogger(Assignment142.class);

    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();
        
        Flux.range(1, 10)
        .flatMap(client::getProduct,1)
        .subscribe(Util.subscriber());
        
        Util.sleepSeconds(10);
    }
}
