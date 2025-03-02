package jayslabs.reactive.sandbox.assignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;


public record BookOrder(
    String genre,
    String title,
    Integer price
) {
    private static final Logger log = LoggerFactory.getLogger(BookOrder.class);

    public static BookOrder create(){
        BookOrder bookOrder = new BookOrder(
            Util.faker().book().genre(),
            Util.faker().book().title(),
            Util.nextInt(50)
        );
        //log.info("Generated book order: {}", bookOrder);
        return bookOrder;
    }

}
