package jayslabs.reactive.sandbox.assignment;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;


/*
 * Solution 2:  
 * The solution provides a template of how to construct a service that 
 * applies varying processing rules depending on the group being processed 
 * and including how to transform the flux itself
 * 
 * Stream PurchaseOrders and group them by category. Apply some business logic to 
 * Kids and Automotive categories.
 * 
 * Kids: 1 free item for every purchase order.
 * Automotive: add 100 to the cart for every purchase order.
 * 
 * Print the purchase order details after processing.
 * 
 */
public class Assignment157GroupedBy {

    private static final Logger log = LoggerFactory.getLogger(Assignment157GroupedBy.class);

    public static void main(String[] args) {
        //solution1();
        solution2();
    }

    /*
     * uses class OrderProcessingService
     */
    public static void solution2(){
        streamPurchaseOrders()
        .filter(PurchaseOrderProcessingService.canProcess())
        .groupBy(PurchaseOrder::category)
        .flatMap(gf -> gf.transform(PurchaseOrderProcessingService.getProcessor(gf.key())))
        .subscribe(Util.subscriber());

        Util.sleepSeconds(20);
    }
    
    public static void solution1(){
        //check only these categories: Kids, Automotive
        var allowedCategories = List.of("Kids", "Automotive");

        streamPurchaseOrders()
        .filter(p -> allowedCategories.contains(p.category()))
        .groupBy(PurchaseOrder::category)
         .flatMap(gf -> processPurchaseOrders(gf))
        .subscribe(Util.subscriber());

        Util.sleepSeconds(20);

    }

    private static Mono<Void> processPurchaseOrders(GroupedFlux<String, PurchaseOrder> gf){
        return gf
        .doOnNext(p ->  processPO(p))
        .doOnComplete(() -> log.info("completed processing purchase orders for category: {}", gf.key()))
        .then();
    }

    private static void processPO(PurchaseOrder po){
        log.info("processing purchase order: {}", po);
        if (po.category().equals("Kids")){
            log.info("1 free item for {}", po);
        }else {
            log.info("added 100 to cart for {}. price is now {}", po, po.price() + 100);
        }
    }

    public static Flux<PurchaseOrder> streamPurchaseOrders(){
        return Flux.interval(Duration.ofMillis(100))
        .map(i -> PurchaseOrder.generate());
    }


}
