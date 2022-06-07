package java8.util.stream;

import java8.util.J8Arrays;
import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.function.Consumer;
import java8.util.function.Predicate;
import java8.util.function.Supplier;
import java8.util.function.UnaryOperator;
import java8.util.stream.Stream;
import java8.util.stream.gr;
import java8.util.stream.hf;
import java8.util.stream.hi;

/* loaded from: classes5.dex */
public final class RefStreams {
    public static <T> Stream<T> takeWhile(Stream<? extends T> stream, Predicate<? super T> predicate) {
        Objects.requireNonNull(stream);
        Objects.requireNonNull(predicate);
        return StreamSupport.stream(new hi.i.d.b(stream.spliterator(), true, predicate), stream.isParallel()).onClose(StreamSupport.a(stream));
    }

    public static <T> Stream<T> dropWhile(Stream<? extends T> stream, Predicate<? super T> predicate) {
        Objects.requireNonNull(stream);
        Objects.requireNonNull(predicate);
        return StreamSupport.stream(new hi.i.d.a(stream.spliterator(), true, predicate), stream.isParallel()).onClose(StreamSupport.a(stream));
    }

    public static <T> Stream.Builder<T> builder() {
        return new hf.h();
    }

    public static <T> Stream<T> empty() {
        return StreamSupport.stream(Spliterators.emptySpliterator(), false);
    }

    public static <T> Stream<T> of(T t) {
        return StreamSupport.stream((Spliterator) new hf.h(t), false);
    }

    public static <T> Stream<T> ofNullable(T t) {
        if (t == null) {
            return empty();
        }
        return StreamSupport.stream((Spliterator) new hf.h(t), false);
    }

    public static <T> Stream<T> of(T... tArr) {
        return J8Arrays.stream(tArr);
    }

    public static <T, S extends T> Stream<T> iterate(final S s, final UnaryOperator<S> unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        return StreamSupport.stream((Spliterator) new Spliterators.AbstractSpliterator<T>(Long.MAX_VALUE, 1040) { // from class: java8.util.stream.RefStreams.1
            S a;
            boolean b;

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super T> consumer) {
                S s2;
                Objects.requireNonNull(consumer);
                if (this.b) {
                    s2 = unaryOperator.apply(this.a);
                } else {
                    Object obj = s;
                    this.b = true;
                    s2 = obj;
                }
                this.a = s2;
                consumer.accept(s2);
                return true;
            }
        }, false);
    }

    public static <T, S extends T> Stream<T> iterate(final S s, final Predicate<? super S> predicate, final UnaryOperator<S> unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        Objects.requireNonNull(predicate);
        return StreamSupport.stream((Spliterator) new Spliterators.AbstractSpliterator<T>(Long.MAX_VALUE, 1040) { // from class: java8.util.stream.RefStreams.2
            S a;
            boolean b;
            boolean c;

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super T> consumer) {
                S s2;
                Objects.requireNonNull(consumer);
                if (this.c) {
                    return false;
                }
                if (this.b) {
                    s2 = unaryOperator.apply(this.a);
                } else {
                    Object obj = s;
                    this.b = true;
                    s2 = obj;
                }
                if (!predicate.test(s2)) {
                    this.a = null;
                    this.c = true;
                    return false;
                }
                this.a = s2;
                consumer.accept(s2);
                return true;
            }

            @Override // java8.util.Spliterators.AbstractSpliterator, java8.util.Spliterator
            public void forEachRemaining(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                if (!this.c) {
                    this.c = true;
                    Object apply = this.b ? unaryOperator.apply(this.a) : s;
                    this.a = null;
                    while (predicate.test(apply)) {
                        consumer.accept(apply);
                        apply = unaryOperator.apply(apply);
                    }
                }
            }
        }, false);
    }

    public static <T> Stream<T> generate(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier);
        return StreamSupport.stream((Spliterator) new gr.f.d(Long.MAX_VALUE, supplier), false);
    }

    public static <T> Stream<T> concat(Stream<? extends T> stream, Stream<? extends T> stream2) {
        Objects.requireNonNull(stream);
        Objects.requireNonNull(stream2);
        return StreamSupport.stream(new hf.b.e(stream.spliterator(), stream2.spliterator()), stream.isParallel() || stream2.isParallel()).onClose(hf.a(stream, stream2));
    }

    private RefStreams() {
    }
}
