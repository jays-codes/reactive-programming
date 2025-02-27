package jayslabs.reactive.sandbox.assignment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.services.Order;
import jayslabs.reactive.sandbox.services.OrderService;
import jayslabs.reactive.sandbox.services.PaymentService;
import jayslabs.reactive.sandbox.services.User;
import jayslabs.reactive.sandbox.services.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
 * Get all users via UserService.getAllUsers() 
 * and build record UserInformation with userId, userName, balance, orders
 * 
 */
public class Assignment146 {
    
    public record UserInformation(
        Integer userId, 
        String userName, 
        Integer balance, 
        List<Order> orders){}

    private static final Logger log = LoggerFactory.getLogger(Assignment146.class);

    public static void main(String[] args) {
        solution2();

    }

    public static void solution1(){
        UserService.getAllUsers()
        .flatMap(user -> Mono.zip(
            Mono.just(user),
            PaymentService.getUserBalance(user.id()),
            Flux.from(OrderService.getUserOrders(user.id()))
            .collectList()
        ))
        .map(tuple -> new UserInformation(
            tuple.getT1().id(), 
            tuple.getT1().name(), 
            tuple.getT2(), 
            tuple.getT3()
        ))
        .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    public static void solution2(){
        getAllUserInformation()
        .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    public static Flux<UserInformation> getAllUserInformation(){
        return UserService.getAllUsers()
        .flatMap(Assignment146::getUserInformation);
}

    private static Mono<UserInformation> getUserInformation(User user){
        var id = user.id();
        return Mono.zip(
            PaymentService.getUserBalance(id),
            OrderService.getUserOrders(id).collectList()

        ).map(tuple -> new UserInformation(
            id,
            user.name(),
            tuple.getT1(),
            tuple.getT2()
        ));
        
    }

}
