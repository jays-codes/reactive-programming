package jayslabs.reactive.sandbox.publisher;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionImpl implements Subscription {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionImpl.class);

    private Subscriber<? super String> subscriber;
    private boolean isCancelled = false;
    
    public SubscriptionImpl(Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {
        logger.info("requesting {}", n);
    }

    @Override
    public void cancel() {
        logger.info("subscriber has cancelled");
    }
}
