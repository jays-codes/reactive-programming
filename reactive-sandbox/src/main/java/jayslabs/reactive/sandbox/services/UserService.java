package jayslabs.reactive.sandbox.services;

import java.util.Map;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for User Microservice simulation 
 * having two endpoints
 * 
 */
public class UserService {

    //userId, userName
    private static final Map<String, Integer> userTable = Map.of(
        "anya", 1,
        "becky", 2,
        "bondo", 3
    );

    /**
     * converts the userTable into a flux of users
     * 1.) fromIterable()
     * 2.) map()
     * @return
     */
    public static Flux<User> getAllUsers(){
        return Flux.fromIterable(userTable.keySet())
            .map(entry -> new User(userTable.get(entry), entry));
    }

    public static Mono<Integer> getUserId(String name){
        //return Mono.justOrEmpty(userTable.get(name));
        
        //because we want side effect to happen at subscription time
        return Mono.fromSupplier(() -> userTable.get(name));
    }
}
