package jayslabs.reactive.sandbox.assignment;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

/**
 * Solution 2. generates revenue report every 3 seconds based on the batch not the overall revenue.
 * Process done in 3-second batches
 */
public class Assignment150Solution2 {

    private static final Logger log = LoggerFactory.getLogger(Assignment150Solution2.class);

    private static final Map<String, Integer> revenueMap = new HashMap<>();

    public static void main(String[] args) {

        //set genres to generate report
        var genres = Set.of("Science Fiction", 
        "Fantasy",
         "Suspense/Thriller");

         streamBookOrders()
         .filter(bookOrder -> genres.contains(bookOrder.genre())).log()
         .buffer(Duration.ofSeconds(3))
         .map(bookOrders -> revenueReport(bookOrders))
         .subscribe(Util.subscriber("subscriber"));

         Util.sleepSeconds(30);
    }

    private static Flux<BookOrder> streamBookOrders(){
        return Flux.interval(Duration.ofMillis(250))
        .map(i -> BookOrder.create());
        //.transform(Util.addFluxLogger("book-orders"));
    }

    private static BookRevenueReport revenueReport(List<BookOrder> orders){
        log.info("Generating revenue report on orders: {}", orders);
        return new BookRevenueReport(
            LocalTime.now(),
            orders.stream()
            .collect(Collectors.groupingBy(BookOrder::genre, Collectors.summingInt(BookOrder::price)))
        );
    }
}
