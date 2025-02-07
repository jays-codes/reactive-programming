package jayslabs.reactive.sandbox.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockSubscriberImpl implements Subscriber<Integer>{


    private static final Logger log = LoggerFactory.getLogger(StockSubscriberImpl.class);

    private Subscription subscription;
    private Integer balance = 1000;
    private int stocks = 0;

    public Subscription getSubscription() {
        return subscription;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
        this.subscription = subscription;
    }

    @Override
    public void onNext(Integer price) { 
        if(price < 90){
            buyStock(price);
            log.info("Buying stock at {}, Balance: {}, stocks: {}", price, balance, stocks);
        } else if(price > 110){
            sellAllStock(price);
            log.info("Selling stock at {}, Balance: {}, stocks: {}", price, balance, stocks);
            this.onComplete();
            getSubscription().cancel();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("error", throwable.getMessage());
    }
    
    @Override
    public void onComplete() {
        log.info("Balance: {}, stocks: {}", balance, stocks);
        log.info("completed: profit/loss: {}", balance - 1000);
    }

    private void buyStock(Integer price){
        if(balance < price){
            log.error("Not enough balance to buy stock");
            return;
        }
        stocks++;
        balance -= price;
    }



    private void sellAllStock(Integer price){
        balance += stocks * price;
        stocks = 0;
    }


    
}
