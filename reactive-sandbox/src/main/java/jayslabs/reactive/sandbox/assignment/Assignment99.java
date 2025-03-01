package jayslabs.reactive.sandbox.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.client.ExternalServiceClient;



public class Assignment99 {
    private static final Logger log = LoggerFactory.getLogger(Assignment99.class);
    private static final ExternalServiceClient client = new ExternalServiceClient();

    public static void main(String[] args) {

        // RevenueService revenueService = new RevenueService();
        // InventoryService inventoryService = new InventoryService();
        
        // Flux<String> revenueflux = Flux.create(revenueService)
        // .interval(Duration.ofSeconds(2))
        // .map(i -> i.toString());
        
        // Flux<String> inventoryflux = Flux.create(inventoryService)
        // .interval(Duration.ofSeconds(2))
        // .map(i -> i.toString());

        // revenueflux.subscribe(Util.subscriber("CFO"));
        // inventoryflux.subscribe(Util.subscriber("Supply Manager"));


        // // Flux<String> ordersflux = client.getOrdersStream()
        // // .doOnNext(order -> {
        // //     log.info("order: {}", order);
        // //     revenueService.computeRevenue(order);
        // //     inventoryService.computeInventory(order);
        // // })
        // // .replay().autoConnect(2);

        // // Process orders with hot publisher

        // Flux<Order> ordersflux = client.getOrdersStream()
        //     .doOnNext(order -> log.info("order: {}", order))
        //     .doOnNext(revenueService::computeRevenue)
        //     .doOnNext(inventoryService::computeInventory)
        //     .replay()
        //     .autoConnect(2);

        // ordersflux.subscribe(Util.subscriber("revenue-service"));
        // ordersflux.subscribe(Util.subscriber("inventory-service"));

        // Util.sleepSeconds(20);
    }
}

// class RevenueService implements Consumer<FluxSink<String>>{

//     //revenue is category:revenue
//     static Map<String, Integer> revenue = new HashMap<>();
//     private static final Logger log = LoggerFactory.getLogger(RevenueService.class);
//     private FluxSink<String> fluxSink;

//     @Override
//     public void accept(FluxSink<String> fluxSink) {
//         this.fluxSink = fluxSink;
//     }

//     public void computeRevenue(Order order){
//         String category = order.category();
//         int price = order.price();
//         int quantity = order.quantity();
//         revenue.merge(category, price * quantity, Integer::sum);

//         //emit the revenue for the category
//         log.info("revenue: {}:{}", category, revenue.get(category));
//         this.fluxSink.next(category + ":" + revenue.get(category));
//     }
// }

// class InventoryService implements Consumer<FluxSink<String>>{

//     private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
//     //inventory is category:quantity is 500
//     static Map<String, Integer> inventory = new HashMap<>();

//     private FluxSink<String> fluxSink;
//     @Override
//     public void accept(FluxSink<String> fluxSink) {
//         this.fluxSink = fluxSink;
//     }

//     public void computeInventory(Order order){
//         //subtract the quantity from the inventory
//         String category = order.category();
//         int quantity = order.quantity();
//         inventory.putIfAbsent(category, 500);
//         inventory.merge(category, -quantity, Integer::sum);

//         //emit the inventory for the category
//         log.info("inventory: {}:{}", category, inventory.get(category));
//         this.fluxSink.next(category + ":" + inventory.get(category));
//     }
// }