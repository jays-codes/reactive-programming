package jayslabs.reactive.sandbox.common;

import java.util.function.UnaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

import reactor.core.publisher.Flux;

public class PubUtil {
    private static final Logger log = LoggerFactory.getLogger(PubUtil.class);
    public static final Faker faker = Faker.instance();

    public static <T> UnaryOperator<Flux<T>> addFluxLogger(String name){
        return flux -> flux
        .doOnSubscribe(sub -> log.info("flux {} - subscribed", name))
        .doOnComplete(() -> log.info("flux {} - completed", name))
        .doOnCancel(() -> log.info("flux {} - cancelled", name))
        .doOnError(err -> log.error("flux {} - error", name, err));
    }

    /**
     * utility method to create a labelled flux
     * @param num
     * @param label
     * @return
    */
    public static Flux<String> createLabelledFlux(int num, String label){
        return Flux.range(1, num)
        .map(i -> label + " - " + i)
        .transform(addFluxLogger(label));
    }


    public static Faker faker(){
        return faker;
    }

}
