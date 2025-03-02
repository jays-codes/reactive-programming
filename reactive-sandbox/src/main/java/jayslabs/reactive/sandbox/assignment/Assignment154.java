package jayslabs.reactive.sandbox.assignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Assignment154 {
    private static final Logger log = LoggerFactory.getLogger(Assignment154.class);

    public static void main(String[] args) {
        streamEvents()
        //.window(10)
        .window(Duration.ofSeconds(2))
        .flatMap(flux -> processEvents(flux))
        .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }
    
    /*
     * create a new file (in project root + resources/files/assignment154 folder)and write events to it
     */
    public static Mono<Void> processEvents(Flux<String> events) {
        log.info("Processing events");
        // Create filename with timestamp format with milliseconds
        String fileName = new SimpleDateFormat("yyyy-MMM-dd-HH-mm-ss-SSS").format(new Date()) + ".txt";
        String filePath = System.getProperty("user.dir") + "/reactive-sandbox/src/main/resources/files/assignment154/" + fileName;
        
        // Create file and directory if needed
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        
        // Create writer outside the stream to use in multiple operators
        AtomicReference<BufferedWriter> writerRef = new AtomicReference<>();
        
        try {
            // Initialize the writer
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writerRef.set(writer);
            
            return events
                .doOnNext(e -> {
                    try {
                        log.info("Writing event: {} to file: {}", e, fileName);
                        writer.write(e);
                        writer.newLine();
                        writer.flush();
                    } catch (IOException ex) {
                        throw Exceptions.propagate(ex);
                    }
                })
                .doOnComplete(() -> {
                    log.info("Window complete, closing file: {}", fileName);
                    try {
                        writer.close();
                    } catch (IOException ex) {
                        throw Exceptions.propagate(ex);
                    }
                })
                .then();
        } catch (IOException ex) {
            return Mono.error(ex);
        }
    }

    /*
     * create a file and write events to a file
     */

    private static Flux<String> streamEvents(){
        return Flux.interval(Duration.ofMillis(100))
        //.take(10)
        .map(i -> "event-" + i);
    }
    
}
