package jayslabs.reactive.sandbox;

import reactor.core.publisher.Mono;
public class MonoEmptyError {

    public static void main(String[] args) {
        // getUserName(1).subscribe(Util.subscriber("anya"));
        // getUserName(2).subscribe(Util.subscriber("anya"));
        // getUserName(3).subscribe(Util.subscriber("anya"));

        //use consumer
        getUserName(3).subscribe(
            s -> System.out.println(s),
            err -> System.out.println(err.getMessage())
        );


    }



    private static Mono<String> getUserName(int userId) {
        
        return switch (userId) {
            case 1 -> Mono.just("John Doe");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("Invalid Input"));
        };
    }
}
