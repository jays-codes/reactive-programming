package jayslabs.reactive.sandbox.publisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublisherImpl implements Publisher<String>{
    private static final Logger logger = LoggerFactory.getLogger(PublisherImpl.class);
    
    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        logger.info("subscribing");
        
        var subscription = new SubscriptionImpl(subscriber);
        subscriber.onSubscribe(subscription);
    }
}
