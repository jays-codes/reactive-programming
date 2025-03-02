package jayslabs.reactive.sandbox.assignment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EventLoggerUtil {

    private final Path path;
    private BufferedWriter writer;

    public EventLoggerUtil(Path path){
        this.path = path;
    }

    public void createFile(){
        try {
            this.writer = Files.newBufferedWriter(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }    

    public void closeFile(){
        try {
            this.writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }   

    public void write(String event){
        try {
            this.writer.write(event);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Mono<Void> processEvents(Flux<String> events, Path path){
        var writer = new EventLoggerUtil(path);
        return events
        .doOnNext(writer::write)
        .doFirst(writer::createFile)
        .doFinally(signalType -> writer.closeFile())
        .then();
    }

}
