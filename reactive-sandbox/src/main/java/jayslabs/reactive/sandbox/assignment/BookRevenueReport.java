package jayslabs.reactive.sandbox.assignment;

import java.time.LocalTime;
import java.util.Map;

public record BookRevenueReport(
    LocalTime time,
    Map<String, Integer> revenueMap
) {

}
