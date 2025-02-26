package jayslabs.reactive.sandbox.common;

import java.time.Duration;
import java.util.function.UnaryOperator;

import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// utility class for creating subscribers with names
public class Util {
    private static final Logger log = LoggerFactory.getLogger(Util.class);
    public static final Faker faker = Faker.instance();


    public static <T> Subscriber<T> subscriber() {
        return new DefaultSubscriber<>("");
    }

    public static <T> Subscriber<T> subscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    // public static <T> Subscriber<T> stockSubscriber(String name, Integer initBalance){
    //     return new DefaultSubscriber<T>(name){
    //         private Integer balance = 1000;
            
            
    //     };
    // }

    public static <T> UnaryOperator<Flux<T>> addFluxLogger(String name){
        return flux -> flux
        .doOnSubscribe(sub -> log.info("flux {} - subscribed", name))
        .doOnComplete(() -> log.info("flux {} - completed", name))
        .doOnCancel(() -> log.info("flux {} - cancelled", name))
        .doOnError(err -> log.error("flux {} - error", name, err));
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

    public static String getProductName(){
        return faker().commerce().productName();
    }

    public static int nextInt(int max){
        return faker().random().nextInt(max);
    }
}

