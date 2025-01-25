package jayslabs.reactive.sandbox;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LazyStream {

    private static final Logger logger = LoggerFactory.getLogger(LazyStream.class);
    public static void main(String[] args) {

        Stream.of(1, 2, 3, 4, 5)
        .peek(i -> logger.info("received : {}", i))
        .filter(i -> i % 2 == 0)
        .peek(i -> logger.info("filtered : {}", i))
        .forEach(i -> logger.info("processed : {}", i));
    }
}
