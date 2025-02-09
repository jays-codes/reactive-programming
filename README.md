# reactive-programming
Jay's proj repo for Reactive Programming in Java

proj: reactive-sandbox

- created FluxGenerate: generate(), defined a lambda for SyncronousSink; fluxCreate2Generate()
- created TakeOperator:  take(), takeWhile(), takeUntil()
- modified FluxCreateDownstreamDemand: added emitOnDemand(), used fluxsink.onRequest() to handle generation and emit of items base on demand (call to request)
- created FluxCreateDownstreamDemand: demos items being eagerly generated on Flux.create(); downstream demand (time of emission) is handled via fluxsink.request()
- updated comments on FluxSinkThreadSafety
- created FluxSinkThreadSafety: demonstrates a thread-safe way to handle multiple producers using FluxSink; used multiple threads to generate names, the "adding to list" logic is via subscribe(list::add)
- created factory to generate Name using Consumer<FluxSink<String>>; refactored FluxCreate to use factory
- created FluxCreate: .create(), emitted items programatically via fluxsink.next()
- Completed Assignment54: read from stock trading service, and buy/sell stock based on price changes; print profit/loss; create Assignment54, StockSubscriberImpl, ExternalServiceClient.getStockPrice()
- created FluxMonoConversion: demonstrates conversion from Mono to Flux: Mono.from(), Flux.from()
- created FluxEmptyError: empty(), error(), defer()
- created FluxInterval: interval()
- created NonBlockingStreamingMessages: demonstrates non-blocking streaming messages; Create getNames():Flux<String> - used external service API to get names; uses AbstractHttpClient for non blocking IO
- create FluxVsList, NameGenerator: demo processing difference bet List and Flux
- created FluxLog: log()
- created FluxRange: range(), map(); Faker to generate names data
- created FluxFromStream: fromStream(), passed stream supplier (list::stream)
- created FluxFromIterableOrArray: fromIterable(), fromArray()
- created FluxMultipleSubscribers class: multiple subscribers,filter(), map()
- created FluxJust class: Flux, just(); subscribe()
- created FileServiceImpl to apply reactive principles to a non-reactive code base; FileServiceImpl2 is a refactored version of FileServiceImpl
- tested block() for synchronous execution
- created WILD demo on writing non-blocking IO running external service client, and calling a "resource intensive" method (takes at least 1 second to execute) 100 times in a loop.
- created AbstractHttpClient - template for reactive http client: HttpClient, LoopResources; created ExternalServiceClient using AbstractHttpClient, getProductName() that return a Mono<String>
- create dockerfile to use vinoth's external-services service; created image and ran container (7070)
- created MonoDefer, createPublisher(), demoed Mono.defer()
- created MonoFromFuture class: demo Mono.fromFuture(); used method reference to enable lazy exec; CompletableFuture, supplyAsync()
- created MonoFromRunnable class: demonstrates Mono.fromRunnable(); integrate side-effecting code (logging, notification, status update) in a reactive pipeline; used Faker
- created MonoFromCallable class: demonstrates Mono.fromCallable(); integrate blocking calls in a reactive pipeline
- created MonoFromSupplier class: demonstrates Mono.fromSupplier(); subscribe(); used for lazy execution
- created MonoEmptyError.getUserName().subscribe() call that takes in a consumer instead of subscriber to demo error handling
- created MonoEmptyEror class to demo publisher that doesn't pass data and simulating error
- created common package, and classes: DefaultSubscriber<T>, Util: class to create subscribers with names; 
- created MonoSubscribe class: demonstrates Mono.subscribe(); implemented diff method signatures of subscribe() to handle base consumer, error, complete, and subscription
- multi type mono demo
- created MonoJust class: demonstrates Mono.just(); subscribe()
- LazyStream class: demonstrates lazy evaluation of streams
- created demo to simulate pub/sub: PubSubDemo
- defined request/cancel logic in SubscriptionImpl
- added: Subscriber, Publisher, Subscription impls
- created. dependencies: Java 21, Reactor, Logback, JUnit
