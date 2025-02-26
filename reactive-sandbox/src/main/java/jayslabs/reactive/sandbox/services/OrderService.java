package jayslabs.reactive.sandbox.services;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

/**
 * Service for Order Microservice simulation 
 * having one endpoint
 * 
 */
public class OrderService {

    //userId, List of Orders
    private static final Map<Integer, List<Order>> orderTable 
    = Map.of(
        1, List.of(
            new Order(1, Util.getProductName(), Util.nextInt(1000)),
            new Order(1, Util.getProductName(), Util.nextInt(1000)),
            new Order(1, Util.getProductName(), Util.nextInt(1000))
        ),
        2, List.of(
            new Order(2, Util.getProductName(), Util.nextInt(1000)),
            new Order(2, Util.getProductName(), Util.nextInt(1000))
        ),
        3, List.of(
            new Order(3, Util.getProductName(), Util.nextInt(1000))
        ),
        4, List.of()
    );

    public static Flux<Order> getUserOrders(Integer userId){
        return Flux.fromIterable(orderTable.get(userId))
            .delayElements(Duration.ofMillis(500));
            //.log();
    }
}