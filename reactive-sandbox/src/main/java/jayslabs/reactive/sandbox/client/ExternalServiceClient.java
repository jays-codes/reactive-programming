package jayslabs.reactive.sandbox.client;

import java.time.Duration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.assignment.Order;
import jayslabs.reactive.sandbox.common.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {
    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient.class);
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

    private Flux<Order> orderFlux;

    public Flux<Order> orderStream(){
        if(Objects.isNull(orderFlux)){
            this.orderFlux = getOrderStream();
        }
        return this.orderFlux;
    }

    private Flux<Order> getOrderStream() {
        return this.httpClient.get()
                              .uri("/demo04/orders/stream")
                              .responseContent()
                              .asString()
                              .map(this::parse)
                              .doOnNext(o -> log.info("{}", o))
                              .publish()
                              .refCount(2);
    }

    /*
        Feel free to make it more robust. This is just a demo
    */
    private Order parse(String message) {
        var arr = message.split(":");
        return new Order(
                arr[1],
                Integer.parseInt(arr[2]),
                Integer.parseInt(arr[3])
        );
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