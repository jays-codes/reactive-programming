package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.services.PaymentService;
import jayslabs.reactive.sandbox.services.UserService;

public class OperatorFlatMapMono {
    private static final Logger log = LoggerFactory.getLogger(OperatorFlatMapMono.class);

    public static void main(String[] args) {

        /* simulates a sequential non-blocking IO call
        *  uses flatMap() to flatten/subscribe to inner publisher
        */
        
        UserService.getUserId("anya")
        /* 
        * below line does not work as getUserBalance() returns Mono<Integer>
        * map() is returning Mono<Mono<Integer>>
        */
        //.map(id -> PaymentService.getUserBalance(id))
        
        .flatMap(id -> PaymentService.getUserBalance(id))
        .subscribe(Util.subscriber());
    }   
}
