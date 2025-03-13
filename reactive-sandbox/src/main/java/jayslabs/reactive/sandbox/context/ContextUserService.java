package jayslabs.reactive.sandbox.context;

import java.util.Map;
import java.util.function.UnaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.util.context.Context;

public class ContextUserService {
    private static final Logger log = LoggerFactory.getLogger(ContextRateLimiter.class);

    private static final Map<String, String> USR_CAT = Map.of(
        "anya", "standard",
        "bondo", "standard",
        "becky", "premium"
    );

    static UnaryOperator<Context> userCategoryContext() {
        return ctx -> ctx.<String>getOrEmpty("user")
            .filter(USR_CAT::containsKey)
            .map(USR_CAT::get)
            .map(category -> ctx.put("category", category))
            .orElse(Context.empty());
    }
}
