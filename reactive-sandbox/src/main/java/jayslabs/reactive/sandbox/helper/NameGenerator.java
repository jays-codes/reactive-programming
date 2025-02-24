package jayslabs.reactive.sandbox.helper;

import java.util.ArrayList;
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
    
    private static final List<String> namesCache = new ArrayList<>();

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

    public static final Flux<String> generateNamesFluxWithDelay(){
        return Flux.generate(sink -> {
            log.info("Generating name");
            Util.sleepSeconds(1);
            var name = Util.faker().name().firstName();
            sink.next(name);
        });
    }

    public static final Flux<String> generateNamesFluxWithCache(){
        return Flux.generate(sink -> {
            log.info("Generating name");
            Util.sleepSeconds(1);

            var name = Util.faker().name().firstName();
            namesCache.add(name);
            sink.next(name);
        }).startWith(namesCache).cast(String.class);
    }

    //generateName()
    public static String generateName(){
        Util.sleepSeconds(1);
        return Util.faker().name().firstName();
    }
    
}
