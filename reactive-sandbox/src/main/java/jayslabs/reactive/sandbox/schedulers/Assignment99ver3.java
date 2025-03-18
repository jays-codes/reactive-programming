package jayslabs.reactive.sandbox.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.assignment.InventoryService;
import jayslabs.reactive.sandbox.assignment.RevenueService;
import jayslabs.reactive.sandbox.client.ExternalServiceClient;
import jayslabs.reactive.sandbox.common.Util;
import reactor.core.scheduler.Schedulers;

//This version uses Schedulers/Threadpools for revenue and inventory services
public class Assignment99ver3 {
    private static final Logger log = LoggerFactory.getLogger(Assignment99ver3.class);
    
    public static void main(String[] args) {

        var client = new ExternalServiceClient();
        var revenueService = new RevenueService();
        var inventoryService = new InventoryService();

        //Flux<Order> ordersflux = client.orderStream();

        client.orderStream()
        .subscribeOn(Schedulers.boundedElastic())
        .subscribe(revenueService::consume);
        
        client.orderStream()
        .subscribeOn(Schedulers.boundedElastic())
        .subscribe(inventoryService::consume);

        inventoryService.stream().subscribe(Util.subscriber("Supply Manager"));
        revenueService.stream().subscribe(Util.subscriber("CFO"));

        Util.sleepSeconds(10);
    }
}





