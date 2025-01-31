# reactive-programming
Jay's proj repo for Reactive Programming in Java

proj: reactive-sandbox
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
