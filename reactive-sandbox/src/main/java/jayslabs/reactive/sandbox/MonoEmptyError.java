package jayslabs.reactive.sandbox;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Mono;
public class MonoEmptyError {

    public static void main(String[] args) {
        getUserName(1).subscribe(Util.subscriber("anya"));
        getUserName(2).subscribe(Util.subscriber("anya"));
        getUserName(3).subscribe(Util.subscriber("anya"));

    }


    private static Mono<String> getUserName(int userId) {
        
        return switch (userId) {
            case 1 -> Mono.just("John Doe");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("Invalid Input"));
        };
    }
}
