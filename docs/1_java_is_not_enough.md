# Java is not enough

Java 8 provides some Language blocks to about `Iterable`, `Collections` and the concept about `Stream`.

**Iterable.java**

```java
package java.lang;

public interface Iterable<T> {

    Iterator<T> iterator();

    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }

    default Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
}
```

Source: https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/lang/Iterable.java

**Collection.java**

```java
package java.util;

public interface Collection<E> extends Iterable<E> {

    // Query Operations

    int size();

    boolean isEmpty();

    boolean contains(Object o);

    Iterator<E> iterator();

    Object[] toArray();

    <T> T[] toArray(T[] a);

    default <T> T[] toArray(IntFunction<T[]> generator) {
        return toArray(generator.apply(0));
    }

    // Modification Operations

    boolean add(E e);

    boolean remove(Object o);

    // Bulk Operations

    boolean containsAll(Collection<?> c);

    boolean addAll(Collection<? extends E> c);

    boolean removeAll(Collection<?> c);

    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    boolean retainAll(Collection<?> c);

    void clear();

    // Comparison and hashing

    boolean equals(Object o);

    int hashCode();

    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}
```

Source: https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/Collection.java

**Stream.java**

```java
package java.util.stream;

public interface Stream<T> extends BaseStream<T, Stream<T>> {

    Stream<T> filter(Predicate<? super T> predicate);

    <R> Stream<R> map(Function<? super T, ? extends R> mapper);

    IntStream mapToInt(ToIntFunction<? super T> mapper);

    LongStream mapToLong(ToLongFunction<? super T> mapper);

    DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);

    <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

    IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);

    LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);

    DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);

    default <R> Stream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
        Objects.requireNonNull(mapper);
        return flatMap(e -> {
            SpinedBuffer<R> buffer = new SpinedBuffer<>();
            mapper.accept(e, buffer);
            return StreamSupport.stream(buffer.spliterator(), false);
        });
    }

    default IntStream mapMultiToInt(BiConsumer<? super T, ? super IntConsumer> mapper) {
        Objects.requireNonNull(mapper);
        return flatMapToInt(e -> {
            SpinedBuffer.OfInt buffer = new SpinedBuffer.OfInt();
            mapper.accept(e, buffer);
            return StreamSupport.intStream(buffer.spliterator(), false);
        });
    }

    default LongStream mapMultiToLong(BiConsumer<? super T, ? super LongConsumer> mapper) {
        Objects.requireNonNull(mapper);
        return flatMapToLong(e -> {
            SpinedBuffer.OfLong buffer = new SpinedBuffer.OfLong();
            mapper.accept(e, buffer);
            return StreamSupport.longStream(buffer.spliterator(), false);
        });
    }

    default DoubleStream mapMultiToDouble(BiConsumer<? super T, ? super DoubleConsumer> mapper) {
        Objects.requireNonNull(mapper);
        return flatMapToDouble(e -> {
            SpinedBuffer.OfDouble buffer = new SpinedBuffer.OfDouble();
            mapper.accept(e, buffer);
            return StreamSupport.doubleStream(buffer.spliterator(), false);
        });
    }

    Stream<T> distinct();

    Stream<T> sorted();

    Stream<T> sorted(Comparator<? super T> comparator);

    Stream<T> peek(Consumer<? super T> action);

    Stream<T> limit(long maxSize);

    Stream<T> skip(long n);

    default Stream<T> takeWhile(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        // Reuses the unordered spliterator, which, when encounter is present,
        // is safe to use as long as it configured not to split
        return StreamSupport.stream(
                new WhileOps.UnorderedWhileSpliterator.OfRef.Taking<>(spliterator(), true, predicate),
                isParallel()).onClose(this::close);
    }

    default Stream<T> dropWhile(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        // Reuses the unordered spliterator, which, when encounter is present,
        // is safe to use as long as it configured not to split
        return StreamSupport.stream(
                new WhileOps.UnorderedWhileSpliterator.OfRef.Dropping<>(spliterator(), true, predicate),
                isParallel()).onClose(this::close);
    }

    void forEach(Consumer<? super T> action);

    void forEachOrdered(Consumer<? super T> action);

    Object[] toArray();

    <A> A[] toArray(IntFunction<A[]> generator);

    T reduce(T identity, BinaryOperator<T> accumulator);

    Optional<T> reduce(BinaryOperator<T> accumulator);

    <U> U reduce(U identity,
                 BiFunction<U, ? super T, U> accumulator,
                 BinaryOperator<U> combiner);

    <R> R collect(Supplier<R> supplier,
                  BiConsumer<R, ? super T> accumulator,
                  BiConsumer<R, R> combiner);

    <R, A> R collect(Collector<? super T, A, R> collector);

    @SuppressWarnings("unchecked")
    default List<T> toList() {
        return (List<T>) Collections.unmodifiableList(new ArrayList<>(Arrays.asList(this.toArray())));
    }

    Optional<T> min(Comparator<? super T> comparator);

    Optional<T> max(Comparator<? super T> comparator);

    long count();

    boolean anyMatch(Predicate<? super T> predicate);

    boolean allMatch(Predicate<? super T> predicate);

    boolean noneMatch(Predicate<? super T> predicate);

    Optional<T> findAny();

    // Static factories

    public static<T> Builder<T> builder() {
        return new Streams.StreamBuilderImpl<>();
    }

    public static<T> Stream<T> empty() {
        return StreamSupport.stream(Spliterators.<T>emptySpliterator(), false);
    }

    public static<T> Stream<T> of(T t) {
        return StreamSupport.stream(new Streams.StreamBuilderImpl<>(t), false);
    }

    public static<T> Stream<T> ofNullable(T t) {
        return t == null ? Stream.empty()
                         : StreamSupport.stream(new Streams.StreamBuilderImpl<>(t), false);
    }

    public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f) {
        Objects.requireNonNull(f);
        Spliterator<T> spliterator = new Spliterators.AbstractSpliterator<>(Long.MAX_VALUE,
               Spliterator.ORDERED | Spliterator.IMMUTABLE) {
            T prev;
            boolean started;

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                Objects.requireNonNull(action);
                T t;
                if (started)
                    t = f.apply(prev);
                else {
                    t = seed;
                    started = true;
                }
                action.accept(prev = t);
                return true;
            }
        };
        return StreamSupport.stream(spliterator, false);
    }

    public static<T> Stream<T> iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next) {
        Objects.requireNonNull(next);
        Objects.requireNonNull(hasNext);
        Spliterator<T> spliterator = new Spliterators.AbstractSpliterator<>(Long.MAX_VALUE,
               Spliterator.ORDERED | Spliterator.IMMUTABLE) {
            T prev;
            boolean started, finished;

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                Objects.requireNonNull(action);
                if (finished)
                    return false;
                T t;
                if (started)
                    t = next.apply(prev);
                else {
                    t = seed;
                    started = true;
                }
                if (!hasNext.test(t)) {
                    prev = null;
                    finished = true;
                    return false;
                }
                action.accept(prev = t);
                return true;
            }

            @Override
            public void forEachRemaining(Consumer<? super T> action) {
                Objects.requireNonNull(action);
                if (finished)
                    return;
                finished = true;
                T t = started ? next.apply(prev) : seed;
                prev = null;
                while (hasNext.test(t)) {
                    action.accept(t);
                    t = next.apply(t);
                }
            }
        };
        return StreamSupport.stream(spliterator, false);
    }

    public static<T> Stream<T> generate(Supplier<? extends T> s) {
        Objects.requireNonNull(s);
        return StreamSupport.stream(
                new StreamSpliterators.InfiniteSupplyingSpliterator.OfRef<>(Long.MAX_VALUE, s), false);
    }

    public static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        @SuppressWarnings("unchecked")
        Spliterator<T> split = new Streams.ConcatSpliterator.OfRef<>(
                (Spliterator<T>) a.spliterator(), (Spliterator<T>) b.spliterator());
        Stream<T> stream = StreamSupport.stream(split, a.isParallel() || b.isParallel());
        return stream.onClose(Streams.composedClose(a, b));
    }

    public interface Builder<T> extends Consumer<T> {

        @Override
        void accept(T t);

        default Builder<T> add(T t) {
            accept(t);
            return this;
        }

        Stream<T> build();

    }
}
```

Source: https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/stream/Stream.java

And others Blocks about Future semantics like `Future`, `CompletionStage` or `CompletableFuture`:

**Future.java**

```java
package java.util.concurrent;

public interface Future<V> {

    boolean cancel(boolean mayInterruptIfRunning);

    boolean isCancelled();

    boolean isDone();

    V get() throws InterruptedException, ExecutionException;

    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```

Source: https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/concurrent/Future.java

**CompletionStage.java**

```java
package java.util.concurrent;

public interface CompletionStage<T> {

    public <U> CompletionStage<U> thenApply(Function<? super T,? extends U> fn);

    public <U> CompletionStage<U> thenApplyAsync
        (Function<? super T,? extends U> fn);

    public <U> CompletionStage<U> thenApplyAsync
        (Function<? super T,? extends U> fn,
         Executor executor);

    public CompletionStage<Void> thenAccept(Consumer<? super T> action);

    public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);

    public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,
                                                 Executor executor);

    public CompletionStage<Void> thenRun(Runnable action);

    public CompletionStage<Void> thenRunAsync(Runnable action);

    public CompletionStage<Void> thenRunAsync(Runnable action,
                                              Executor executor);

    public <U,V> CompletionStage<V> thenCombine
        (CompletionStage<? extends U> other,
         BiFunction<? super T,? super U,? extends V> fn);

    public <U,V> CompletionStage<V> thenCombineAsync
        (CompletionStage<? extends U> other,
         BiFunction<? super T,? super U,? extends V> fn);

    public <U,V> CompletionStage<V> thenCombineAsync
        (CompletionStage<? extends U> other,
         BiFunction<? super T,? super U,? extends V> fn,
         Executor executor);

    public <U> CompletionStage<Void> thenAcceptBoth
        (CompletionStage<? extends U> other,
         BiConsumer<? super T, ? super U> action);

    public <U> CompletionStage<Void> thenAcceptBothAsync
        (CompletionStage<? extends U> other,
         BiConsumer<? super T, ? super U> action);

    public <U> CompletionStage<Void> thenAcceptBothAsync
        (CompletionStage<? extends U> other,
         BiConsumer<? super T, ? super U> action,
         Executor executor);

    public CompletionStage<Void> runAfterBoth(CompletionStage<?> other,
                                              Runnable action);

    public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,
                                                   Runnable action);

    public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,
                                                   Runnable action,
                                                   Executor executor);
    public <U> CompletionStage<U> applyToEither
        (CompletionStage<? extends T> other,
         Function<? super T, U> fn);

    public <U> CompletionStage<U> applyToEitherAsync
        (CompletionStage<? extends T> other,
         Function<? super T, U> fn);

    public <U> CompletionStage<U> applyToEitherAsync
        (CompletionStage<? extends T> other,
         Function<? super T, U> fn,
         Executor executor);

    public CompletionStage<Void> acceptEither
        (CompletionStage<? extends T> other,
         Consumer<? super T> action);

    public CompletionStage<Void> acceptEitherAsync
        (CompletionStage<? extends T> other,
         Consumer<? super T> action);

    public CompletionStage<Void> acceptEitherAsync
        (CompletionStage<? extends T> other,
         Consumer<? super T> action,
         Executor executor);

    public CompletionStage<Void> runAfterEither(CompletionStage<?> other,
                                                Runnable action);

    public CompletionStage<Void> runAfterEitherAsync
        (CompletionStage<?> other,
         Runnable action);

    public CompletionStage<Void> runAfterEitherAsync
        (CompletionStage<?> other,
         Runnable action,
         Executor executor);

    public <U> CompletionStage<U> thenCompose
        (Function<? super T, ? extends CompletionStage<U>> fn);

    public <U> CompletionStage<U> thenComposeAsync
        (Function<? super T, ? extends CompletionStage<U>> fn);

    public <U> CompletionStage<U> thenComposeAsync
        (Function<? super T, ? extends CompletionStage<U>> fn,
         Executor executor);

    public <U> CompletionStage<U> handle
        (BiFunction<? super T, Throwable, ? extends U> fn);

    public <U> CompletionStage<U> handleAsync
        (BiFunction<? super T, Throwable, ? extends U> fn);

    public <U> CompletionStage<U> handleAsync
        (BiFunction<? super T, Throwable, ? extends U> fn,
         Executor executor);

    public CompletionStage<T> whenComplete
        (BiConsumer<? super T, ? super Throwable> action);

    public CompletionStage<T> whenCompleteAsync
        (BiConsumer<? super T, ? super Throwable> action);

    public CompletionStage<T> whenCompleteAsync
        (BiConsumer<? super T, ? super Throwable> action,
         Executor executor);

    public CompletionStage<T> exceptionally
        (Function<Throwable, ? extends T> fn);

    public default CompletionStage<T> exceptionallyAsync
        (Function<Throwable, ? extends T> fn) {
        return handle((r, ex) -> (ex == null)
                      ? this
                      : this.<T>handleAsync((r1, ex1) -> fn.apply(ex1)))
            .thenCompose(Function.identity());
    }

    public default CompletionStage<T> exceptionallyAsync
        (Function<Throwable, ? extends T> fn, Executor executor) {
        return handle((r, ex) -> (ex == null)
                      ? this
                      : this.<T>handleAsync((r1, ex1) -> fn.apply(ex1), executor))
            .thenCompose(Function.identity());
    }

    public default CompletionStage<T> exceptionallyCompose
        (Function<Throwable, ? extends CompletionStage<T>> fn) {
        return handle((r, ex) -> (ex == null)
                      ? this
                      : fn.apply(ex))
            .thenCompose(Function.identity());
    }

    public default CompletionStage<T> exceptionallyComposeAsync
        (Function<Throwable, ? extends CompletionStage<T>> fn) {
        return handle((r, ex) -> (ex == null)
                      ? this
                      : this.handleAsync((r1, ex1) -> fn.apply(ex1))
                        .thenCompose(Function.identity()))
            .thenCompose(Function.identity());
    }

    public default CompletionStage<T> exceptionallyComposeAsync
        (Function<Throwable, ? extends CompletionStage<T>> fn,
         Executor executor) {
        return handle((r, ex) -> (ex == null)
                      ? this
                      : this.handleAsync((r1, ex1) -> fn.apply(ex1), executor)
                        .thenCompose(Function.identity()))
            .thenCompose(Function.identity());
    }

    public CompletableFuture<T> toCompletableFuture();

}
```

Source: https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/concurrent/CompletionStage.java
