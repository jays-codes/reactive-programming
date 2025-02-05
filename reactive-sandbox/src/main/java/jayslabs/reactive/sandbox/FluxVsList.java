package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.helper.NameGenerator;
import jayslabs.reactive.sandbox.subscriber.SubscriberImpl;


public class FluxVsList {
    private static final Logger log = LoggerFactory.getLogger(FluxVsList.class);

    public static void main(String[] args) {

        // List<String> names = NameGenerator.generateNamesList(10);
        // log.info(names.toString());

        // demo using flux
        // NameGenerator.generateNamesFlux(10).subscribe(Util.subscriber());

        var subscriber = new SubscriberImpl();
        NameGenerator.generateNamesFlux(10).subscribe(subscriber);

        subscriber.getSubscription().request(5);

    }
}
