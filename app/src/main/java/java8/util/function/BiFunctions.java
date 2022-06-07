package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class BiFunctions {
    public static <R, T, U, V> BiFunction<T, U, V> andThen(BiFunction<? super T, ? super U, ? extends R> biFunction, Function<? super R, ? extends V> function) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(function);
        return b.a(function, biFunction);
    }

    private BiFunctions() {
    }
}
