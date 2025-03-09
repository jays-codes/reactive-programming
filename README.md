# reactive-programming
Jay's proj repo for Reactive Programming in Java

proj: reactive-sandbox

- SinkOne: emitValue(), define EmitFailureHandler
- package reactive.sandbox.sinks; SinkOne class: Sinks.one(), asMono(), tryEmitValue(), tryEmitEmpty(), tryEmitError()
- Assignment164RepeatRetry, ExternalServiceClient06 classes: modfied ESC to add use for new endpoints, demo repeat() with takeUntil(), retry() and filter() on custom exceptions
- OperatorRetry : retry(n), retry(predicate), retryWhen(Retry), Retry : indefinitely(), max(n), fixedDelay(n, Duration), doBeforeRetry(), filter(predicate), onRetryExhaustedThrow(), signal.failure()
- OperatorRepeat : added use of repeat with predicate, repeatWhen(function)
- OperatorRepeat : repeat()
- Assignment157GroupBy - [TEMPLATE QUALITY] answer for #157 assignment; GroupFlux processing via separate Service class; service maintains state via PO_PROCESSOR map Map<String, UnaryOperator<Flux<PurchaseOrder>>> (Category, methodInvocation)
- OperatorGroupBy class: groupBy():GroupFlux<String, Integer> demoed processing of GroupFlux<String, Integer> using flatMap(), doOnNext(), doOnComplete(), then():Mono<Void>
- Assignment154Solution2 class: solution 2 - create EventLoggerUtil to separate filehandling and window processing. Appropriate file handling methods called on doOnNext(), doFirst(), doFinally() used AtomicInteger to increment filename
- Assignment154 class: solution 1 - modify processEvents() to contain logic for writing window processing result to a file (1 window:1 file)
- OperatorWindow class: window(), processEvents(Flux<String>):Mono<Void> 
to process each flux window created
- Assignment150Solution2 class: solution #2 - generates revenue report on batch of BookOrders emitted - 3-second batches. no revenueMap maintained.
- Assignment150 class: solution #1 - stream BookOrders, separate flux emitting every 3 seconds, passing immutable copy of revenueMap
- OperatorBuffer class: buffer(), Flux.never(), bufferTime()
- Assignment146: create a Flux of UserInformation records, aggregating user/balance/orders into a UserInformation record, for all users. Used Flux.flatMap(), Mono.zip(), Mono.map(), Flux.collectList(). two solutions  
- OperatorThen: then(), sendNotification() to show routing
- OperatorCollectList : collectList()
- OperatorConcatMap : concatMap()
- Assignment142: modified Assignment136 to use Flux<Integer> instead of a for-loop, and use flatMap()
- OperatorFlatMapFlux: added version setting concurrency val for flatMap()
- OperatorFlatMapFlux: from Flux<User> get Flux<Order> for all User via flatMap(OrderService::getUserOrders); added fluxLogger to OrderService via transform()
- OperatorFlatMapMany: used flatMapMany() mono to flux
- OperatorFlatMapMono to use mock Microservices, used flatMap() against Mono<Integer> return from a call to UserService, and call to getUserBalance(), returning a Mono<Integer> 
- created Microservice simulation for flatMap() demo: records - Order, User; classes - UserService, OrderService, PaymentService
- Assignment136 ver.2; made product into a record;
- Assignment136 ver.1; modified ExternalServiceClient added method for new endpoint (get product/price/review)
- OperatorZip class: record Robot, zip(), zip() with combinator (.map()); PubUtil util with createLabelledFlux() to generate Flux that emits a number of strings labelled with given label and count
- OperatorMerge: getFlightsDemo(); Flight record, AirCanada, Emirates, JAL classes with getFlights():Flux<Flight>; Priceline class to return merged Flux<Flight> and limit emission via take(Duration);
- OperatorMerge: merge(), mergeWith(); modified Util to add addFluxLogger() for use in transform()
- OperatorConcatWith: concatDelayError()
- OperatorConcatWith; used concatWith(), concat()
- OperatorStartWith: added nameGeneratorWithCacheDemo() and modified NameGenrator to add method returning cache-enabled publisher to show publisher caching with startWith()
- OperatorStartWith: startWith(Publisher), startWith(T)
- Assignment99ver2: iteration 2; modified ExternalServiceClient added singleton factory method to return Flux<Order> (Hot Publisher via publish().refCount()); Created OrderProcessor interface, Order record, Impls of OrderProcessor: RevenueService, InventoryService with map var, order processing (consume()) and emit (stream() - Cold Publisher) logic; cleaned up previous version and remove internal classes causing clashing dependencies
- Assignment99: iteration 1 - Modified ExternalServiceClient: getOrderStream():Flux<String> to retrieve from orders endpoint; Created RevenueService and InventoryService to compute revenue and inventory; used Flux.create() to create revenue and inventory fluxes; used multiple subscribers to subscribe to the fluxes; used share() to create a hot publisher; used replay() to replay the flux to new subscribers
- FluxCreateMultipleSubscribers: share() to create a hot publisher, enabling multiple subscribers to subscribe to a flux created via Flux.create(), passing in a Consumer<FluxSink<String>> (NameGenerator)
- HotPublisherCache: replay(), used stockStream() to generate stock prices; used stockFlux to emit stock prices to multiple subscribers
- HotPublisherAutoConnect: autoConnect(2)
- HotPublisher: refCount()
- HotPublisher: demonstrates hot publisher, and multiple subscribers; used movieStream() to generate movie scenes; used share() to create a hot publisher
- ColdPublisher: demonstrates cold publisher, and multiple subscribers; used NameGenerator to generate names, demo flux sink issue not being able to support multi subscribers
- Assignment88: modified ExternalServiceClient.getProductNameVer3() to handle timeout and empty fallback by calling different external service endpoints; used switchIfEmpty() and timeout()
- OperatorTransform: created custom reuseable generic operator to encapsulate common logic for adding debug logging to a flux, call method conditionally inside transform(), used identity function
- Timeout: timeout(Duration), timeout(Duration, Publisher)
- SwitchIfEmpty: switchIfEmpty(Publisher)
- DefaultIfEmpty: defaultIfEmpty(T)
- SubscribeTest78, ErrorHandling: onErrorReturn(), onErrorResume() - using custom fallback(Function) method; added onErrorComplete() and onErrorContinue(BiConsumer)
- OperatorDelay class; delayElements()
- created HooksAndCallbacks: doOnSubscribe(), doOnRequest(), doOnNext(), doOnError(), doOnComplete(), doOnTerminate(), doOnCancel(), doOnDiscard(), doFinally(); separate doDiscard() 
- created Assignment73: emit till 'Canada' problem using handle()
- created Operators, OperatorHandle: handle(handler)
- Completed Assignment68: implement FileReaderService interface: FileReaderServiceImpl, used Flux.generate(stateSupplier, generator, stateConsumer); set BufferedReader as state. Wrote subscriber code with downstream demand handling.
- refactored previous assignments
- created FluxGenerateWithState: generate(), defined a lambda for SyncronousSink and state; defined state inside generate()
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
- [TEMPLATE QUALITY] created WILD demo on writing non-blocking IO running external service client, and calling a "resource intensive" method (takes at least 1 second to execute) 100 times in a loop.
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
