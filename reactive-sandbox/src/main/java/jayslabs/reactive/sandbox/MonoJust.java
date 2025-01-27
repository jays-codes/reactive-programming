package jayslabs.reactive.sandbox;

import jayslabs.reactive.sandbox.subscriber.SubscriberImpl;
import reactor.core.publisher.Mono;

public class MonoJust {
    public static void main(String[] args) {
        //monoDemo1();
        monoDemo2();
    }

    public static void monoDemo1() {
        var mono = Mono.just("MONO HWorld!!!");
       
        var subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);
        
        subscriber.getSubscription().request(5);

        //System.out.println(mono);
        //mono.subscribe(System.out::println);
    }

    public static void monoDemo2() {
        Mono.just("This is MONO!!!")
        .subscribe(str -> System.out.println("Received: " + str + "; size:" 
        +str.length()));
    }
}
