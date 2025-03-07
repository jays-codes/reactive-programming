package jayslabs.reactive.sandbox.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClientResponse;

public class ExternalServiceClient06 extends AbstractHttpClient {
    //record Product(String name, String price, String review){}
    
    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient.class);


    public Mono<String> getCountry(){
        return get("/demo06/country");
    }

    public Mono<String> getProductName(int productId){
        return get("/demo06/product/" + productId);
    }

    private Mono<String> get(String path){
        return this.httpClient.get()
            .uri(path)
            .response(this::toResponse)
            .next();
    }

    private Flux<String> toResponse(HttpClientResponse response, ByteBufFlux body){
        return switch (response.status().code()) {
            case 200 -> body.asString();
            case 400 -> Flux.error(new ClientError());
            default -> Flux.error(new ServerError());
        };
    }



}