package jayslabs.reactive.sandbox.client;

import java.time.Duration;

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

    public Flux<String> getOrdersStream(){
        return this.httpClient.get()
            .uri("/demo04/orders/stream")
            .responseContent()
            .asString();
    }

    //return Mono<String> of productName passing in productId
    public Mono<String> getProductNameVer2(int productId){
        return this.httpClient.get()
            .uri("/demo03/product/" + productId)
            .responseContent().asString().next();
    }
    public Mono<String> getProductNameVer3(int productId){
        var defaultPath = "/demo03/product/" + productId;
        var timeoutPath = "/demo03/timeout-fallback/product/" + productId;
        var emptyPath = "/demo03/empty-fallback/product/" + productId;

        return getProductName(defaultPath)
            .timeout(Duration.ofSeconds(2),getProductName(timeoutPath))
            .switchIfEmpty(getProductName(emptyPath));
    }

    public Mono<String> getProductName(String path){
        return this.httpClient.get()
            .uri(path)
            .responseContent().asString().next();
    }


    public Mono<String> getProductNameViaTimeoutFallback(int productId){
        return this.httpClient.get()
            .uri("/demo03/timeout-fallback/product/" + productId)
            .responseContent().asString().next();
    }

    public Mono<String> getProductNameViaEmptyFallback(int productId){
        return this.httpClient.get()
            .uri("/demo03/empty-fallback/product/" + productId)
            .responseContent().asString().next();
    }

}