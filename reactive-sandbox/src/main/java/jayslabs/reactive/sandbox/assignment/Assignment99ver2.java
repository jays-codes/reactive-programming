package jayslabs.reactive.sandbox.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.client.ExternalServiceClient;
import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
public class Assignment99ver2 {
    private static final Logger log = LoggerFactory.getLogger(Assignment99ver2.class);
    
    public static void main(String[] args) {

        var client = new ExternalServiceClient();
        var revenueService = new RevenueService();
        var inventoryService = new InventoryService();

        Flux<Order> ordersflux = client.orderStream();

        ordersflux.subscribe(revenueService::consume);
        ordersflux.subscribe(inventoryService::consume);

        inventoryService.stream().subscribe(Util.subscriber("Supply Manager"));
        revenueService.stream().subscribe(Util.subscriber("CFO"));

        Util.sleepSeconds(10);
    }
}





