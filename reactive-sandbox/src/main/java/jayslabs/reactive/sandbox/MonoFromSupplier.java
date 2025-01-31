package jayslabs.reactive.sandbox;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;


public class MonoFromSupplier {
    private static final Logger logger = LoggerFactory.getLogger(MonoFromSupplier.class);


    public static void main(String[] args) {
        var list = List.of(1, 2, 3, 4, 5);
        //Mono.just(sum(list));
            //.subscribe(Util.subscriber("anya"));
        Mono.fromSupplier(() -> sum(list));
    }


    private static int sum(List<Integer> list) {
        logger.info("summing list");
        return list.stream()
            .mapToInt(Integer::intValue)
            .sum();
    }
}
