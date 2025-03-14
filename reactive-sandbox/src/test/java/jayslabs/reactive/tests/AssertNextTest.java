package jayslabs.reactive.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class AssertNextTest {
    private static final Logger log = LoggerFactory.getLogger(AssertNextTest.class);

    record Book(int id, String author, String title){}

    private Flux<Book> getBooks(){
        return Flux.range(1,10)
        .map(i -> new Book(i, Util.faker().book().author(), Util.faker().book().title()))
        .log();
    }

    @Test
    public void testAssertNext(){
        StepVerifier.create(getBooks())
        .assertNext(book -> {
            Assertions.assertTrue(book.id() >= 1 && book.id() <= 10);
            Assertions.assertNotNull(book.author());
            Assertions.assertNotNull(book.title());
        })
        .thenConsumeWhile(book -> book.id() <= 10 && book.id() >= 1 && !book.title().equals(""))
        .verifyComplete();
    }
    
}
