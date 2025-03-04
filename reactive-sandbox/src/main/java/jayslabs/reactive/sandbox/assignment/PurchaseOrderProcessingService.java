package jayslabs.reactive.sandbox.assignment;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PurchaseOrderProcessingService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderProcessingService.class);

    private static final Map<String, UnaryOperator<Flux<PurchaseOrder>>> PO_PROCESSOR = Map.of(
        "Kids", processKidsPO(),
        "Automotive", processAutomotivePO()
    );

    private static UnaryOperator<Flux<PurchaseOrder>> processAutomotivePO(){
        return flux -> flux
        .map(po -> new PurchaseOrder(po.item(), po.category(), po.price() + 100));
    }

    private static UnaryOperator<Flux<PurchaseOrder>> processKidsPO(){
        return flux -> flux
        .flatMap(order -> getSameItemFree(order).flux().startWith(order));
    }

    private static Mono<PurchaseOrder> getSameItemFree(PurchaseOrder po){
        return Mono.fromSupplier(() -> new PurchaseOrder(
            po.item(), 
            po.category(), 
            0));
    }

    public static Predicate<PurchaseOrder> canProcess(){
        return po -> PO_PROCESSOR.containsKey(po.category());
    }

    public static UnaryOperator<Flux<PurchaseOrder>> getProcessor(String category){
        return PO_PROCESSOR.get(category);
    }
}
