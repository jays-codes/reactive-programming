package jayslabs.reactive.sandbox.assignment;

import jayslabs.reactive.sandbox.common.Util;

public record PurchaseOrder(
    String item, 
    String category, 
    Integer price) {

    public static PurchaseOrder generate(){
        return new PurchaseOrder(
            Util.faker().commerce().productName(), 
            Util.faker().commerce().department(), 
            Util.nextInt(500));
    }
}
