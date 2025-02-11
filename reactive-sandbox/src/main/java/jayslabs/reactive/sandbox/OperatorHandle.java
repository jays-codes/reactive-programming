package jayslabs.reactive.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class OperatorHandle {
    private static final Logger log = LoggerFactory.getLogger(OperatorHandle.class);

    public static void main(String[] args) {
        oddEven()
        .subscribe(Util.subscriber());
        processNumbers()
        .subscribe(Util.subscriber());
    }

    public static Flux<String> oddEven() {
        Flux<Integer> fluxint = Flux.range(1, 10);
        Flux<String> fluxstr = fluxint.handle((num,sink) -> {
            if (num % 2 == 0) {
                sink.next(num + " is even");
            } else {
                sink.next(num + " is odd");
            }
        });
        return fluxstr;
    }

    public static Flux<Integer> processNumbers() {
        Flux<Integer> flux = Flux.range(1, 10)
        .handle((i, sink) -> {
            switch (i) {
                case 1 -> sink.next(-2);
                case 4 -> {}
                case 7 -> sink.error(new RuntimeException("error"));
                default -> sink.next(i);
            }
        });
     
        return flux;
    }
}
