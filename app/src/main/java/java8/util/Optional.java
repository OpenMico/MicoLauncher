package java8.util;

import java.util.NoSuchElementException;
import java8.util.function.Consumer;
import java8.util.function.Function;
import java8.util.function.Predicate;
import java8.util.function.Supplier;
import java8.util.stream.RefStreams;
import java8.util.stream.Stream;

/* loaded from: classes5.dex */
public final class Optional<T> {
    private static final Optional<?> a = new Optional<>(null);
    private final T b;

    public static <T> Optional<T> empty() {
        return (Optional<T>) a;
    }

    private Optional(T t) {
        this.b = t;
    }

    public static <T> Optional<T> of(T t) {
        return new Optional<>(Objects.requireNonNull(t));
    }

    public static <T> Optional<T> ofNullable(T t) {
        return t == null ? (Optional<T>) a : new Optional<>(t);
    }

    public T get() {
        return orElseThrow();
    }

    public boolean isPresent() {
        return this.b != null;
    }

    public boolean isEmpty() {
        return this.b == null;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        Object obj = (T) this.b;
        if (obj != null) {
            consumer.accept(obj);
        }
    }

    public void ifPresentOrElse(Consumer<? super T> consumer, Runnable runnable) {
        Object obj = (T) this.b;
        if (obj != null) {
            consumer.accept(obj);
        } else {
            runnable.run();
        }
    }

    public Optional<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (isPresent() && !predicate.test((T) this.b)) {
            return empty();
        }
        return this;
    }

    public <U> Optional<U> map(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function);
        if (!isPresent()) {
            return empty();
        }
        return ofNullable(function.apply((T) this.b));
    }

    public <U> Optional<U> flatMap(Function<? super T, ? extends Optional<? extends U>> function) {
        Objects.requireNonNull(function);
        if (!isPresent()) {
            return empty();
        }
        return (Optional) Objects.requireNonNull((Optional) function.apply((T) this.b));
    }

    public Optional<T> or(Supplier<? extends Optional<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        return isPresent() ? this : (Optional) Objects.requireNonNull((Optional) supplier.get());
    }

    public Stream<T> stream() {
        if (!isPresent()) {
            return RefStreams.empty();
        }
        return RefStreams.of(this.b);
    }

    public T orElse(T t) {
        T t2 = this.b;
        return t2 != null ? t2 : t;
    }

    public T orElseGet(Supplier<? extends T> supplier) {
        T t = this.b;
        return t != null ? t : (T) supplier.get();
    }

    public T orElseThrow() {
        T t = this.b;
        if (t != null) {
            return t;
        }
        throw new NoSuchElementException("No value present");
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> supplier) throws Throwable {
        T t = this.b;
        if (t != null) {
            return t;
        }
        throw ((Throwable) supplier.get());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Optional)) {
            return false;
        }
        return Objects.equals(this.b, ((Optional) obj).b);
    }

    public int hashCode() {
        return Objects.hashCode(this.b);
    }

    public String toString() {
        T t = this.b;
        return t != null ? String.format("Optional[%s]", t) : "Optional.empty";
    }
}
