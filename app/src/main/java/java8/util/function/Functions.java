package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class Functions {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Object a(Object obj) {
        return obj;
    }

    public static <R, T, V> Function<V, R> compose(Function<? super T, ? extends R> function, Function<? super V, ? extends T> function2) {
        Objects.requireNonNull(function);
        Objects.requireNonNull(function2);
        return p.a(function, function2);
    }

    public static <R, T, V> Function<T, V> andThen(Function<? super T, ? extends R> function, Function<? super R, ? extends V> function2) {
        Objects.requireNonNull(function);
        Objects.requireNonNull(function2);
        return q.a(function2, function);
    }

    public static <T> Function<T, T> identity() {
        return r.a();
    }

    private Functions() {
    }
}
