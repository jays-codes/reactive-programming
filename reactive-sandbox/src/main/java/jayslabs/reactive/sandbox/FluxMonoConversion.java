package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
public class FluxMonoConversion {

    private static final Logger log = LoggerFactory.getLogger(FluxMonoConversion.class);

    public static void main(String[] args) {
         
        //need conversion from Mono to Flux
        //save(getUserName(1));

        var mono = getUserName(4);
        save(monoToFlux(mono));
        
    }


    private static <T> Flux<T> monoToFlux(Mono<T> mono){
        return Flux.from(mono);
    }

    
    private static Mono<String> getUserName(int userId){
        return switch(userId){

            case 1 -> Mono.just("sam");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("Not implemented"));
        };
    }

    private static void save(Flux<String> flux){
        flux.subscribe(Util.subscriber());
    }
    
}

