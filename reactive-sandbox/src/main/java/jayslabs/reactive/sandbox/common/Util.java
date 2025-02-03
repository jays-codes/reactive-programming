package jayslabs.reactive.sandbox.common;

import java.time.Duration;

import org.reactivestreams.Subscriber;

import com.github.javafaker.Faker;

import reactor.core.publisher.Mono;

// utility class for creating subscribers with names
public class Util {

    public static final Faker faker = Faker.instance();


    public static <T> Subscriber<T> subscriber() {
        return new DefaultSubscriber<>("");
    }

    public static <T> Subscriber<T> subscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    public static Faker faker(){
        return faker;
    }


    public static void sleepSeconds(int seconds){
        try {
            Thread.sleep(Duration.ofSeconds(seconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var mono = Mono.just(3);
        mono.subscribe(subscriber("subscriber1"));
        mono.subscribe(subscriber("subscriber2"));


    }
}

