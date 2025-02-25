package jayslabs.reactive.sandbox.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.client.ExternalServiceClient;
import jayslabs.reactive.sandbox.common.Util;

public class Assignment136 {
    private static final Logger log = LoggerFactory.getLogger(Assignment136.class);

    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();

        for (int i = 0; i <= 10; i++) {
            client.getProduct(i).subscribe(Util.subscriber());
        }

        Util.sleepSeconds(10);
    }
}
