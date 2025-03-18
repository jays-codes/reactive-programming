package jayslabs.reactive.sandbox.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.client.ExternalServiceClient;
import jayslabs.reactive.sandbox.common.Util;
import reactor.core.scheduler.Schedulers;

public class EventLoopIssueFix {

    private static final Logger log = LoggerFactory.getLogger(EventLoopIssueFix.class);

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        for (int i = 1; i <= 5; i++) {
            client.getProductNameAndLog(i)
            .publishOn(Schedulers.boundedElastic())
            .map(EventLoopIssueFix::process)
            .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(15);
    }

    private static String process(String input){
        //simulate this as time consuming task
        Util.sleepSeconds(1);
        return input + "-processed";
    }
}
