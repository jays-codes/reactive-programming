package jayslabs.reactive.sandbox;

import java.util.logging.Logger;

import jayslabs.reactive.sandbox.client.ExternalServiceClient;
import jayslabs.reactive.sandbox.common.Util;

public class NonBlockingIO {


    private static final Logger log = Logger.getLogger(NonBlockingIO.class.getName());


    public static void main(String[] args) {
     
        var client = new ExternalServiceClient();

        // client.getProductName(33)
        // .subscribe(Util.subscriber());

        for (int i=1;i<=10;i++){
            client.getProductName(i);
            //.block();
            log.info("Completed: " + i);
            //.subscribe(Util.subscriber());
        }


        Util.sleepSeconds(2);
    }
    
}
