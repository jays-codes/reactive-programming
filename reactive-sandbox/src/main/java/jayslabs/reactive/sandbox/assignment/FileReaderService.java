package jayslabs.reactive.sandbox.assignment;

import java.nio.file.Path;

import reactor.core.publisher.Flux;

public interface FileReaderService {
    Flux<String> read(Path path);
}

