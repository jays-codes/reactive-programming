package jayslabs.reactive.sandbox.helper;

import java.util.List;
import java.util.stream.IntStream;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class NameGenerator {


    public static final List<String> generateNamesList(int count){
        return IntStream.rangeClosed(0, count)
            .mapToObj(i -> generateName())
            .toList();
    }
    
    public static final Flux<String> generateNamesFlux(int count){
        return Flux.range(0, count)
            .map(i -> generateName());
    }

    


    //simulating a resource intensive operation (takes 1 second to execute)
    private static String generateName() {
        Util.sleepSeconds(1);
        return Util.faker().name().firstName();
    }
    
}
