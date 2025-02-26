package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.services.OrderService;
import jayslabs.reactive.sandbox.services.UserService;

public class OperatorFlatMapMany {
    private static final Logger log = LoggerFactory.getLogger(OperatorFlatMapMany.class);

    public static void main(String[] args) {
        UserService.getUserId("becky")
        .flatMapMany(id -> OrderService.getUserOrders(id))

        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }
}
