package jayslabs.reactive.sandbox.helper;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class NameGenerator implements Consumer<FluxSink<String>>{

    private FluxSink<String> fluxSink;
    private static final Logger log = LoggerFactory.getLogger(NameGenerator.class);
    @Override
    public void accept(FluxSink<String> fluxSink) {
        log.info("accept() method called");
        this.fluxSink = fluxSink;
    }

    //create items to emit via fluxSink.next()
    public void generateQuickName(){
        this.fluxSink.next(Util.faker().name().firstName());
    }

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
