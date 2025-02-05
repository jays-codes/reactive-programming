package jayslabs.reactive.sandbox;

import jayslabs.reactive.sandbox.client.ExternalServiceClient;
import jayslabs.reactive.sandbox.common.Util;

public class NonBlockingStreamingMessages {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();
        client.getNames().subscribe(Util.subscriber("anya"));
        client.getNames().subscribe(Util.subscriber("becky"));

        Util.sleepSeconds(10);
    }
}

