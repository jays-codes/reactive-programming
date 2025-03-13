package jayslabs.reactive.sandbox.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClientResponse;

public class ExternalServiceClient07 extends AbstractHttpClient {
    //record Product(String name, String price, String review){}
    
    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient07.class);


    public Mono<String> getBook(){
        return getBookFlux()
            .next();
    }


    public Flux<String> getBookFlux(){
        return this.httpClient.get()
            .uri("/demo07/book")
            .responseContent()
            .asString();
    }

    private Flux<String> toResponse(HttpClientResponse response, ByteBufFlux body){
        return switch (response.status().code()) {
            case 200 -> body.asString();
            case 400 -> Flux.error(new ClientError());
            default -> Flux.error(new ServerError());
        };
    }



}