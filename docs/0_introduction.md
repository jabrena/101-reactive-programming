# Introduction

## What is Reactive Programming?

> Reactive programming is a declarative programming paradigm concerned with data streams and the propagation of change. With this paradigm, it's possible to express static (e.g., arrays) or dynamic (e.g., event emitters) data streams with ease, and also communicate that an inferred dependency within the associated execution model exists, which facilitates the automatic propagation of the changed data flow.

Source: https://en.wikipedia.org/wiki/Reactive_programming


## What is Reactive Streams?

> Reactive Streams is an initiative to provide a standard for asynchronous stream processing with non-blocking back pressure. This encompasses efforts aimed at runtime environments (JVM and JavaScript) as well as network protocols.

Source: https://www.reactive-streams.org/
Source: https://github.com/reactive-streams/reactive-streams-jvm

![](images/reactive-stream.png)

**Publisher.java**

```java
package org.reactivestreams;

public interface Publisher<T> {

    public void subscribe(Subscriber<? super T> s);
}
```

**Subscriber.java**

```java
package org.reactivestreams;

public interface Subscriber<T> {

    public void onSubscribe(Subscription s);

    public void onNext(T t);

    public void onError(Throwable t);

    public void onComplete();
}
```

**Subscription.java**

```java
package org.reactivestreams;

public interface Subscription {

    public void request(long n);

    public void cancel();
}
```

**Processor.java**

```java
package org.reactivestreams;

public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {

}
```

## Reactive Manifesto?

> The Reactive Manifesto is a document that defines the core principles of reactive programming. A Reactive system, need to be Responsive, Resilient, Elastic & Message Driven.

![](images/reactive-traits.svg)

Source: https://www.reactivemanifesto.org/


## Reactive Foundation

> As part of the Linux Foundation, the Reactive Foundation is a vendor-neutral home for Reactive projects and initiatives and is dedicated to being a catalyst for advancing a new landscape of technologies, standards, and vendors. Reactive Foundation is committed to improving the developer experience of designing and building applications and systems based on the Reactive Manifesto and Reactive Principles.

Source: https://www.reactive.foundation/