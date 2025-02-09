package jayslabs.reactive.sandbox.assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

public class Assignment68 {
    private static final Logger log = LoggerFactory.getLogger(Assignment68.class);

    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        fileReaderService
        .read(Path.of("reactive-sandbox/src/main/resources/files/file.txt"))
        .takeUntil(line -> line.contains("line900"))
        .subscribe(Util.subscriber());


    }
}

/**
 * implement FileReaderService
 * 
 * do the work only when it is subscribed
 * do the work based on demand
 * stop producing when subscriber cancels
 * produce only the requested items
 * file should be closed once done
 */
class FileReaderServiceImpl implements FileReaderService {
    private static final Logger log = LoggerFactory.getLogger(FileReaderServiceImpl.class);

    @Override
    public Flux<String> read(Path path) {
        return Flux.generate(

            () -> openFile(path),
            this::readLines, 
            this::closeFile
        );
    }


    private BufferedReader readLines(BufferedReader reader, SynchronousSink<String> sink) {
        try {
            var line = reader.readLine();
            if (line != null) {
                sink.next(line);
            } else {
                sink.complete();
            }
        } catch (IOException e) {
            sink.error(e);
        }
        return reader;

    }

    private BufferedReader openFile(Path path) throws IOException {
        log.info("Opening file: {}", path);
        return new BufferedReader(new FileReader(path.toFile()));
    }

    private void closeFile(BufferedReader reader)  {
        try {
            reader.close();
            log.info("File closed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

