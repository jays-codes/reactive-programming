package jayslabs.reactive.sandbox.assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Mono;

public class FileServiceImpl implements FileService {

    private final String BASE_PATH = "C:\\jay\\temp\\";

    private final Logger log = Logger.getLogger(FileServiceImpl.class.getName());

    public static void main(String[] args) {
        var fileService = new FileServiceImpl();
        fileService.read("test.txt")
        .subscribe(Util.subscriber());

        fileService.write("test2.txt", "HWORLD!!!!")
        .subscribe();

        //fileService.delete("test2.txt").subscribe();
    }

    @Override

    public Mono<String> read(String fileName) {
        log.log(Level.INFO, "Reading file: {0}", fileName);
        return Mono.fromSupplier(() -> {
            try {
                return Files.readString(Path.of(BASE_PATH, fileName));

            } catch (IOException e) {
                throw new RuntimeException("Error reading file: " + fileName, e);
            }
        });

    }


    @Override
    public Mono<Void> write(String fileName, String content) {
        log.log(Level.INFO, "Writing file: {0}", fileName);
        return Mono.fromRunnable(() -> {
            try {


                Files.writeString(Path.of(BASE_PATH, fileName), content);
            } catch (IOException e) {
                throw new RuntimeException("Error writing file: " + fileName, e);
            }
        });
    }


    @Override
    public Mono<Void> delete(String fileName) {
        log.log(Level.INFO, "Deleting file: {0}", fileName);
        return Mono.fromRunnable(() -> {
            try {
                Files.delete(Path.of(BASE_PATH, fileName));
            } catch (IOException e) {

                throw new RuntimeException("Error deleting file: " + fileName, e);
            }
        });
    }
    
    
    

    
}
