package jayslabs.reactive.sandbox.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.client.ExternalServiceClient;
import jayslabs.reactive.sandbox.common.Util;
import jayslabs.reactive.sandbox.subscriber.StockSubscriberImpl;

// read from stock service emmiting price changes (bet 80-120) every 500ms for 20secs
// 1. create subscriber with 1000$ balance
// 2. buy stock if price drops below 90
// 3. sell stock if price rises above 110
//  - sell all
//  - cancel subscription
//  - print profit/loss

public class Assignment54 {
    private static final Logger log = LoggerFactory.getLogger(Assignment54.class);

    public static void main(String[] args) {
        var client = new ExternalServiceClient();
        var subscriber = new StockSubscriberImpl();
        client.getStockPrice()
            .subscribe(subscriber);

        Util.sleepSeconds(20);

        }


}

