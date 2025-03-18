package jayslabs.reactive.sandbox.client;

import java.time.Duration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.assignment.Order;
import jayslabs.reactive.sandbox.assignment.Product;
import jayslabs.reactive.sandbox.common.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {
    //record Product(String name, String price, String review){}
    
    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient.class);
    
    public Mono<String> getProductName(int productId){
        return this.httpClient.get()

        .uri("/demo01/product/" + productId)
        .responseContent().asString()
        .next();
    }

    public Mono<String> getProductNameAndLog(int productId){
        return this.httpClient.get()

        .uri("/demo01/product/" + productId)
        .responseContent().asString()
        .doOnNext(msg -> log.info("next: {}", msg))
        .next();
        //.publishOn(Schedulers.boundedElastic());
    }

    public Mono<String> getCountry(){
        return this.httpClient.get()
            .uri("/demo06/country")
            .responseContent().asString().next();
    }

    public Mono<Product> getProduct(int productId){
        return Mono.zip(
            getProductName05(productId), 
            getReview(productId), 
            getPrice(productId))
            .map(tuple -> new Product(tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    private Mono<String> getProductName05(int productId){
        return this.httpClient.get()
            .uri("/demo05/product/" + productId)
            .responseContent().asString().next();
    }

    public Mono<String> getProductName06(int productId){
        return this.httpClient.get()
            .uri("/demo06/product/" + productId)
            .responseContent().asString().next();
    }

    private Mono<String> getPrice(int productId){
        return this.httpClient.get()
            .uri("/demo05/price/" + productId)
            .responseContent().asString().next();
    }

    private Mono<String> getReview(int productId){
        return this.httpClient.get()
            .uri("/demo05/review/" + productId)
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