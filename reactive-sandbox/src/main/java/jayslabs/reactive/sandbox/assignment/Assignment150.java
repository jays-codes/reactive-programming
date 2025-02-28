package jayslabs.reactive.sandbox.assignment;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;

public class Assignment150 {

    //genre, revenue
    private static final Map<String, Integer> revenueMap = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(Assignment150.class);

    record BookOrder(
        String genre,
        String title,
        Integer price
    ){}


    private static final List<String> genres = List.of(
        "Science Fiction",
        "Fantasy",
        "Suspense/Thriller"
    );
    public static void main(String[] args) {
        // streamBookOrders()
        //     .concatMap(bookOrder -> Mono.just(revenueMap))
        //     //.take(1)
        //     //emit the revenue map every 3 seconds
        //     .buffer(Duration.ofSeconds(3))
        //     .subscribe(Util.subscriber("subscriber"));

        // Util.sleepSeconds(30);


        /*
         * call streamBookOrders() and emit the revenue map every 3 seconds
         */
        Flux<BookOrder> streamBookOrders = streamBookOrders();
        streamBookOrders
        .then()
        .subscribe(Util.subscriber());


        Flux.interval(Duration.ofSeconds(3))
        .doOnNext(i -> log.info("Revenue report at {} seconds: {}", i*3, revenueMap))
        .map(i -> Map.copyOf(revenueMap))  // Create an immutable copy of the current state
        .subscribe(Util.subscriber("revenue-report"));

        Util.sleepSeconds(20);
    }

    

    private static void updateRevenueReport(BookOrder bookOrder) {
        String genre = bookOrder.genre();
        Integer price = bookOrder.price();
        revenueMap.merge(genre, price, Integer::sum);

    }

    private static Flux<BookOrder> streamBookOrders(){
        return Flux.interval(Duration.ofMillis(500))
        .map(i -> generateBookOrder())
        .transform(Util.addFluxLogger("book-orders"));
    }

    private static BookOrder generateBookOrder(){
        BookOrder bookOrder = new BookOrder(
            generateRandomGenre(),
            Util.faker().book().title(),
            Util.nextInt(50)
        );
        updateRevenueReport(bookOrder);
        log.info("Generated book order: {}", bookOrder);
        return bookOrder;
    }

    private static String generateRandomGenre(){
        return genres.get(Util.nextInt(genres.size()));
    }
}
