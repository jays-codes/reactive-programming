package jayslabs.reactive.sandbox.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ParallelExecution {

    private static final Logger log = LoggerFactory.getLogger(ParallelExecution.class);

    public static void main(String[] args) {

        var flux = Flux.range(1, 10);
        flux
        .parallel()
        .runOn(Schedulers.parallel())
        .map(ParallelExecution::process)
        .sequential()
        .map(i -> "processed: " + i)
        .subscribe(Util.subscriber("anya"));
        
        Util.sleepSeconds(5);
    }

    private static int process(int i){
        Util.sleepSeconds(1);
        return i * i;
    }
}
