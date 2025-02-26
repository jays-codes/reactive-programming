package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.services.OrderService;
import jayslabs.reactive.sandbox.services.User;
import jayslabs.reactive.sandbox.services.UserService;
public class OperatorFlatMapFlux {
    private static final Logger log = LoggerFactory.getLogger(OperatorFlatMapFlux.class);

    public static void main(String[] args) {
        //get all users via UserService and with the ids, get all orders from OrderService

        //option 1
        UserService.getAllUsers()
        .map(User::id)
        .flatMap(OrderService::getUserOrders)
        .subscribe(Util.subscriber());

        //option 2
        // UserService.getAllUsers()
        // .flatMap(user -> OrderService.getUserOrders(user.id()))
        // .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }
}
