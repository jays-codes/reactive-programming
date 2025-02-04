package jayslabs.reactive.sandbox.assignment;

import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Mono;


public class FileServiceImpl2 implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl2.class);

    private static final Path PATH = Path.of("src/main/resources/files");
    


    public static void main(String[] args) {
        var fileService = new FileServiceImpl2();
        fileService.read("test.txt").subscribe(Util.subscriber());
    }

    @Override
    public Mono<String> read(String fileName) {
        log.info("Reading file: {}", fileName);
        return Mono.fromCallable(() -> {
                return Files.readString(PATH.resolve(fileName));
        });
    }

    @Override
    public Mono<Void> write(String fileName, String content) {
        log.info("Writing file: {}", fileName);
        return Mono.fromRunnable(() -> {
            writeFile(fileName, content);
        });
    }

    @Override
    public Mono<Void> delete(String fileName) {
        log.info("Deleting file: {}", fileName);
        return Mono.fromRunnable(() -> {
            deleteFile(fileName);
        });
    }


    private void writeFile(String fileName, String content) {
        try {
            Files.writeString(PATH.resolve(fileName), content);
            log.info("File written: {}", fileName);
        } catch (Exception e) {
            throw new RuntimeException("Error writing file: " + fileName, e);
        }

    }

    private void deleteFile(String fileName) {
        try {
            Files.delete(PATH.resolve(fileName));
            log.info("File deleted: {}", fileName);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file: " + fileName, e);
        }
    }

}

