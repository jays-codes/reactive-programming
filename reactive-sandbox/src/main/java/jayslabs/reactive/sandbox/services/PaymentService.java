package jayslabs.reactive.sandbox.services;

import java.util.Map;

import reactor.core.publisher.Mono;

/**
 * Service for Payment Microservice simulation 
 * having one endpoint
 * 
 */
public class PaymentService {

    //map userId, balance
    private static final Map<Integer, Integer> balanceTable = Map.of(
        1, 300,
        2, 420,
        3, 200
    );

    public static Mono<Integer> getUserBalance(Integer userId){
        //return Mono.just(balanceTable.get(userId));

        //because we want side effect to happen at subscription time
        return Mono.fromSupplier(() -> balanceTable.get(userId));
    }
    
    
}
