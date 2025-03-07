package jayslabs.reactive.sandbox.client;

public class ClientError extends RuntimeException {

    public ClientError() {
        super("bad request");
    }

}
