package jayslabs.reactive.sandbox.client;

import jayslabs.reactive.sandbox.common.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<String> getProductName(int productId){
        return this.httpClient.get()

        .uri("/demo01/product/" + productId)
        .responseContent().asString().next();
    }

    public Flux<String> getNames(){
        return this.httpClient.get()
            .uri("/demo02/name/stream")
            .responseContent().asString();
    }

    //return Flux<Integer> from getStockPrice API
    public Flux<Integer> getStockPrice(){
        return this.httpClient.get()
            .uri("/demo02/stock/stream")
            .responseContent()
            .asString()
            .map(Integer::parseInt);
    }





}