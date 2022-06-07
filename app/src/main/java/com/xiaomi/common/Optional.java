package com.xiaomi.common;

import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
public final class Optional<T> {
    private static final Optional<?> a = new Optional<>();
    private final T b;

    private Optional() {
        this.b = null;
    }

    public static <T> Optional<T> empty() {
        return (Optional<T>) a;
    }

    private Optional(T t) {
        if (t != null) {
            this.b = t;
            return;
        }
        throw new NullPointerException();
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Optional)) {
            return false;
        }
        T t = this.b;
        T t2 = ((Optional) obj).b;
        if (t != t2) {
            return t != null && t.equals(t2);
        }
        return true;
    }

    public int hashCode() {
        T t = this.b;
        if (t == null) {
            return 0;
        }
        return t.hashCode();
    }

    public String toString() {
        T t = this.b;
        return t != null ? String.format("Optional[%s]", t) : "Optional.empty";
    }
}
