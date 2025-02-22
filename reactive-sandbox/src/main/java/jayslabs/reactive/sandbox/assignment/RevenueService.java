package jayslabs.reactive.sandbox.assignment;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;      // Check this import

public class RevenueService implements OrderProcessor{

    //revenueDB is category:revenue
    private final Map<String, Integer> revenueDB = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(RevenueService.class);
   
    @Override
    public void consume(Order order) {
        String category = order.category();
        int price = order.price();
        int quantity = order.quantity();
        revenueDB.merge(category, price * quantity, Integer::sum);

        log.info("revenue: {}:{}", category, revenueDB.get(category));
    }

    @Override
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(2))
        .map(i -> this.revenueDB.toString());
    }

}
