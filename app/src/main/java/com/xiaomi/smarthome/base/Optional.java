package com.xiaomi.smarthome.base;

import java.util.NoSuchElementException;
import java.util.Objects;

/* loaded from: classes4.dex */
public class Optional<T> {
    private static final Optional<?> a = new Optional<>();
    private final T b;

    private Optional() {
        this.b = null;
    }

    public static <T> Optional<T> empty() {
        return (Optional<T>) a;
    }

    private Optional(T t) {
        this.b = (T) Objects.requireNonNull(t);
    }

    public static <T> Optional<T> of(T t) {
        return new Optional<>(t);
    }

    public static <T> Optional<T> ofNullable(T t) {
        return t == null ? empty() : of(t);
    }

    public T get() {
        T t = this.b;
        if (t != null) {
            return t;
        }
        throw new NoSuchElementException("No value present");
    }

    public boolean isPresent() {
        return this.b != null;
    }

    public T orElse(T t) {
        T t2 = this.b;
        return t2 != null ? t2 : t;
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
