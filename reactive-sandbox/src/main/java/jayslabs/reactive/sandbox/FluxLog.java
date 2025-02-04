package jayslabs.reactive.sandbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
public class FluxLog {
    private static final Logger log = LoggerFactory.getLogger(FluxLog.class);

    public static void main(String[] args) {
        Flux.range(1, 5)
            .log()
            .subscribe(Util.subscriber());
    }
}
