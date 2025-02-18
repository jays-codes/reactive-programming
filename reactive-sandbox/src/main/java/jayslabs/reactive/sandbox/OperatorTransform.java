package jayslabs.reactive.sandbox;

import java.util.function.UnaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class OperatorTransform {
    private static final Logger log = LoggerFactory.getLogger(OperatorTransform.class);

    record Customer(int id,String name) {}
    record PurchaseOrder(String prodName, int price, int quantity) {}

    public static void main(String[] args) {

        var isDebugOn = true;

        getCustomers()
        .doOnNext(c -> log.info("received customer: {}", c))
        .transform(isDebugOn ? addDebugger() : flux -> flux)
        .subscribe();

        getPurchaseOrders()
        .doOnNext(p -> log.info("received purchase order: {}", p))
        .transform(isDebugOn ? addDebugger() : flux -> flux)
        .subscribe();
    }
    
    private static Flux<Customer> getCustomers(){
        return Flux.range(1, 5)
        .map(i -> new Customer(i, Util.faker().name().fullName()));
    }

    private static Flux<PurchaseOrder> getPurchaseOrders(){
        return Flux.range(1, 5)
        .map(i -> new PurchaseOrder(
            Util.faker().commerce().productName(), 
            i, i*5));
    }

    private static <T> UnaryOperator<Flux<T>> addDebugger(){
        return flux -> flux      
        
        .doOnComplete(() -> log.info("getCustomers COMPLETED"))
        .doOnError(e -> log.error("error: ", e));
    }
}
