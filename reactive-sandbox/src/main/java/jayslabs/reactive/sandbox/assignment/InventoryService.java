package jayslabs.reactive.sandbox.assignment;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

public class InventoryService implements OrderProcessor{

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    //inventory is category:quantity is 500
    private final Map<String, Integer> inventoryDB = new HashMap<>();

    @Override
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(2))
        .map(i -> this.inventoryDB.toString());
    }

    @Override      
    public void consume(Order order){
        //option 1: subtract the quantity from the inventory
        String category = order.category();
        int quantity = order.quantity();
        inventoryDB.putIfAbsent(category, 500);
        inventoryDB.merge(category, -quantity, Integer::sum);

        //option 2 get current inventory and compute the new inventory
        // String category = order.category();
        // int currentInventory = inventoryDB.getOrDefault(category,500);
        // int newInventory = currentInventory - order.quantity();
        // inventoryDB.put(category, newInventory);

        //emit the inventory for the category
        log.info("inventory: {}:{}", category, inventoryDB.get(category));
    }
}
