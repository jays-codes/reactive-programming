package jayslabs.reactive.sandbox;

import java.time.Duration;

import jayslabs.reactive.sandbox.publisher.PublisherImpl;
import jayslabs.reactive.sandbox.subscriber.SubscriberImpl;

public class PubSubDemo {
    public static void main(String[] args) throws InterruptedException {
        demo4();
    }

    public static void demo1(){
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();

        publisher.subscribe(subscriber);
    }

    public static void demo2() throws InterruptedException{
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();

        publisher.subscribe(subscriber);

        subscriber.getSubscription().request(4);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(2);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(3);
    }

    public static void demo3() throws InterruptedException{
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();

        publisher.subscribe(subscriber);

        subscriber.getSubscription().request(4);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(2);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().cancel();

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(3);
    }

    public static void demo4() throws InterruptedException{
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();

        publisher.subscribe(subscriber);

        subscriber.getSubscription().request(4);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(11);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));

        subscriber.getSubscription().request(5);
    }
}
